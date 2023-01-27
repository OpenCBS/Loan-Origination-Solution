package com.opencbs.genesis.repositories.implementations;

import com.opencbs.genesis.domain.Event;
import com.opencbs.genesis.domain.User;
import com.opencbs.genesis.domain.UserProfileView;
import com.opencbs.genesis.domain.events.UserEventParticipants;
import com.opencbs.genesis.dto.EventParticipantsDto;
import com.opencbs.genesis.repositories.customs.EventRepositoryCustom;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alopatin on 19-Apr-17.
 */
public class EventRepositoryImpl extends BaseRepository implements EventRepositoryCustom {
    protected EventRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Event.class);
    }

    @Override
    public Page<EventParticipantsDto> findAvailableParticipants(String query, User currentUser, Pageable pageable) {
        if (StringUtils.isEmpty(query)) {
            query = "";
        }
        Query queryTotal = this.getEntityManager().createQuery("select count(upv.id) from UserProfileView upv where lower(upv.fullName) like lower(:query) and (upv.isUser <> true or upv.entityId <>:id)");
        queryTotal.setParameter("id", currentUser.getId());
        queryTotal.setParameter("query", "%" + query + "%");
        long total = (long) queryTotal.getSingleResult();

        TypedQuery<UserProfileView> queryFiltered = this.getEntityManager().createQuery("select upv from UserProfileView upv where  lower(upv.fullName) like lower(:query) and (upv.isUser <> true or  upv.entityId <>:id) order by upv.firstName desc, upv.lastName desc", UserProfileView.class);
        queryFiltered.setParameter("id", currentUser.getId());
        queryFiltered.setParameter("query", "%" + query + "%");
        queryFiltered.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        queryFiltered.setMaxResults(pageable.getPageSize());

        List<EventParticipantsDto> participants = queryFiltered.getResultList()
                .stream()
                .map(x -> new EventParticipantsDto(x.getEntityId(),
                                                   x.getFullName(),
                                                   x.getEmail(),
                                                   x.isUser()))
                .collect(Collectors.toList());
        return new PageImpl<>(participants, pageable, total);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Event> findAllBy(int participantId, Date startDate, Date endDate) {
        Criteria criteria = createCriteria("e");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        Criteria participantsCriteria = criteria.createCriteria("e.participants", "p",
                JoinType.LEFT_OUTER_JOIN
        );

        Criterion participantsCriterion = Restrictions.and(
                Restrictions.eq("class", UserEventParticipants.class),
                Restrictions.eq("p.user.id", participantId)
        );


        Criterion participantsOrCreatedByCriterion = Restrictions.or(
                participantsCriterion,
                Restrictions.eq("e.createdUser.id",participantId));

        Criterion criterionDate = Restrictions.between("e.startDate", startDate, endDate);
        Criterion criterion = Restrictions.and(criterionDate, participantsOrCreatedByCriterion);

        participantsCriteria.add(criterion);

        return criteria.list();
    }
}

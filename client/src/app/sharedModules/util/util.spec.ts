import * as util from './util';

describe('utility', () => {

  it('`toBoolean`', () => {
    const { toBoolean } = util;
    expect(toBoolean('')).toBe(true);
    expect(toBoolean('false')).toBe(false);
    expect(toBoolean('0')).toBe(false);

    expect(toBoolean(true)).toBe(true);
    expect(toBoolean(false)).toBe(false);
  });

  it('`isInt`', () => {
    const { isInt } = util;

    expect(isInt(10)).toBe(true);
    expect(isInt('10')).toBe(true);
    expect(isInt(' 1 ')).toBe(true);
    expect(isInt('0')).toBe(true);
    expect(isInt(4e2)).toBe(true);
    expect(isInt('4e2')).toBe(true);
    expect(isInt('10e-1')).toBe(true);

    expect(isInt(10.5)).toBe(false);
    expect(isInt('10.5')).toBe(false);
    expect(isInt('  ')).toBe(false);
    expect(isInt('')).toBe(false);
    expect(isInt('1a')).toBe(false);
    expect(isInt('4e2a')).toBe(false);
    expect(isInt('4e-2')).toBe(false);
    expect(isInt(null)).toBe(false);
    expect(isInt(undefined)).toBe(false);
    expect(isInt(NaN)).toBe(false);
  });

  it('`isObject`', () => {
    expect(util.isObject({})).toBe(true);
    expect(util.isObject([1, 2, 3])).toBe(true);
    expect(util.isObject(function(){})).toBe(true);

    expect(util.isObject(null)).toBe(false);
    expect(util.isObject('string')).toBe(false);
    expect(util.isObject(10)).toBe(false);
  });

  it('uniqueId', () => {
    const [prefix, id, count] = util.uniqueId('pr1').split('_');
    expect(prefix).toBe('ngl');
    expect(id).toBe('pr1');
    expect(count).toBeGreaterThan(0);

    expect(util.uniqueId('pr1')).toBe(`ngl_pr1_${+count + 1}`);
    expect(util.uniqueId('pr2')).toBe(`ngl_pr2_${+count + 2}`);
    expect(util.uniqueId()).toBe(`ngl_uid_${+count + 3}`);
  });

  describe('replaceClass', () => {
    let instance: any, { replaceClass } = util;

    beforeEach(() => {
      instance = {
        renderer: { setElementClass: jasmine.createSpy('setElementClass') },
        element: { nativeElement: null },
      };
    });

    it('will replace classes if supplied', () => {
      replaceClass(instance, null, null);
      expect(instance.renderer.setElementClass).not.toHaveBeenCalled();

      replaceClass(instance, 'c1', 'c2');
      expect(instance.renderer.setElementClass).toHaveBeenCalledWith(null, 'c1', false);
      expect(instance.renderer.setElementClass).toHaveBeenCalledWith(null, 'c2', true);
    });

    it('will not remove class if not needed', () => {
      replaceClass(instance, 'c3', 'c3');
      expect(instance.renderer.setElementClass).not.toHaveBeenCalledWith(null, 'c3', false);
      expect(instance.renderer.setElementClass).toHaveBeenCalledWith(null, 'c3', true);
    });

    it('will add multiple classes if array is passed', () => {
      replaceClass(instance, null, ['c5', 'c6']);
      expect(instance.renderer.setElementClass).toHaveBeenCalledWith(null, 'c5', true);
      expect(instance.renderer.setElementClass).toHaveBeenCalledWith(null, 'c6', true);
    });

    it('will remove multiple classes if array is passed', () => {
      replaceClass(instance, ['r1', 'r2'], null);
      expect(instance.renderer.setElementClass).toHaveBeenCalledWith(null, 'r1', false);
      expect(instance.renderer.setElementClass).toHaveBeenCalledWith(null, 'r2', false);
    });
  });
});

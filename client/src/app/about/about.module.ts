import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AboutComponent } from './about.component';

const homeRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'about',
    component: AboutComponent
  }
]);

@NgModule({
  imports: [
    homeRouting
  ],
  declarations: [
    AboutComponent
  ],
  providers: []
})
export class AboutModule {}

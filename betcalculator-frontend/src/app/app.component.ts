import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'vedemo che succedeeee';
  num = 4;
  myData = [
      {name: 'Facebook',location: 'www.facebook.com'},
      {name: 'Youtube', location: 'www.youtube.com'}
      ];

      onClick(){
        this.num = 6;
      }
       
}

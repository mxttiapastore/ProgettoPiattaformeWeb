import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ComponentiListComponent } from './componenti-list.component';

describe('ComponentiListComponent', () => {
  let component: ComponentiListComponent;
  let fixture: ComponentFixture<ComponentiListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComponentiListComponent], // standalone
    }).compileComponents();

    fixture = TestBed.createComponent(ComponentiListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

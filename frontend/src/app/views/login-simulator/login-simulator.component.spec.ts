import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginSimulatorComponent } from './login-simulator.component';

describe('LoginSimulatorComponent', () => {
  let component: LoginSimulatorComponent;
  let fixture: ComponentFixture<LoginSimulatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginSimulatorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginSimulatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

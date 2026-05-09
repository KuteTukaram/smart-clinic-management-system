import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../models/user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  isLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      fullName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^\+?[1-9]\d{9,14}$/)]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(50)]],
      confirmPassword: ['', [Validators.required]],
      role: ['PATIENT', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  get fullName(): AbstractControl {
    return this.registerForm.get('fullName')!;
  }

  get email(): AbstractControl {
    return this.registerForm.get('email')!;
  }

  get phone(): AbstractControl {
    return this.registerForm.get('phone')!;
  }

  get password(): AbstractControl {
    return this.registerForm.get('password')!;
  }

  get confirmPassword(): AbstractControl {
    return this.registerForm.get('confirmPassword')!;
  }

  get role(): AbstractControl {
    return this.registerForm.get('role')!;
  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');
    
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    }
    
    if (confirmPassword) {
      confirmPassword.setErrors(null);
    }
    
    return null;
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      this.markFormGroupTouched(this.registerForm);
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    const registerRequest: RegisterRequest = {
      fullName: this.fullName.value,
      email: this.email.value,
      phone: this.phone.value,
      password: this.password.value,
      confirmPassword: this.confirmPassword.value,
      role: this.role.value
    };

    this.authService.register(registerRequest).subscribe({
      next: (response) => {
        if (response.success) {
          this.successMessage = 'Registration successful! Redirecting to dashboard...';
          setTimeout(() => {
            this.router.navigate(['/dashboard']);
          }, 1500);
        } else {
          this.errorMessage = response.message || 'Registration failed. Please try again.';
        }
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'An error occurred during registration. Please try again.';
        this.isLoading = false;
      }
    });
  }

  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      control?.markAsTouched();
    });
  }

  getErrorMessage(control: AbstractControl): string {
    if (control.hasError('required')) {
      return 'This field is required';
    }
    if (control.hasError('email')) {
      return 'Please enter a valid email address';
    }
    if (control.hasError('minlength')) {
      if (control === this.fullName) {
        return 'Full name must be at least 2 characters';
      }
      if (control === this.password) {
        return 'Password must be at least 6 characters';
      }
      return 'Minimum length not met';
    }
    if (control.hasError('maxlength')) {
      if (control === this.fullName) {
        return 'Full name must not exceed 100 characters';
      }
      if (control === this.password) {
        return 'Password must not exceed 50 characters';
      }
      return 'Maximum length exceeded';
    }
    if (control.hasError('pattern')) {
      return 'Please enter a valid phone number (e.g., +1234567890)';
    }
    if (control.hasError('passwordMismatch')) {
      return 'Passwords do not match';
    }
    return '';
  }
}

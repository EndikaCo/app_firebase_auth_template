package com.endcodev.beautifullogin.presentation.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationUtilTest {

  @Test
fun `enableSignUp returns true when all inputs are valid and terms are accepted`() {
    val validEmail = "test@email.com"
    val validPassword = "password123"
    val validName = "John Doe"
    val termsAccepted = true

    assertTrue(ValidationUtil.enableSignUp(validEmail, validPassword, validName, termsAccepted))
}

@Test
fun `enableSignUp returns false when email is invalid`() {
    val invalidEmail = "invalid"
    val validPassword = "password123"
    val validName = "John Doe"
    val termsAccepted = true

    assertFalse(ValidationUtil.enableSignUp(invalidEmail, validPassword, validName, termsAccepted))
}

@Test
fun `enableSignUp returns false when password is too short`() {
    val validEmail = "test@email.com"
    val shortPassword = "pass"
    val validName = "John Doe"
    val termsAccepted = true

    assertFalse(ValidationUtil.enableSignUp(validEmail, shortPassword, validName, termsAccepted))
}

@Test
fun `enableSignUp returns false when name is too short`() {
    val validEmail = "test@email.com"
    val validPassword = "password123"
    val shortName = "Jo"
    val termsAccepted = true

    assertFalse(ValidationUtil.enableSignUp(validEmail, validPassword, shortName, termsAccepted))
}

@Test
fun `enableSignUp returns false when terms are not accepted`() {
    val validEmail = "test@email.com"
    val validPassword = "password123"
    val validName = "John Doe"
    val termsNotAccepted = false

    assertFalse(ValidationUtil.enableSignUp(validEmail, validPassword, validName, termsNotAccepted))
}

@Test
fun `enableLogin returns true when email and password are valid`() {
    val validEmail = "test@email.com"
    val validPassword = "password123"

    assertTrue(ValidationUtil.enableLogin(validEmail, validPassword))
}

@Test
fun `enableLogin returns false when email is invalid`() {
    val invalidEmail = "invalid"
    val validPassword = "password123"

    assertFalse(ValidationUtil.enableLogin(invalidEmail, validPassword))
}

@Test
fun `enableLogin returns false when password is too short`() {
    val validEmail = "test@email.com"
    val shortPassword = "pass"

    assertFalse(ValidationUtil.enableLogin(validEmail, shortPassword))
}
}
// shared/src/commonMain/kotlin/com/cis/cmp/ui/authentication/LoginScreen.kt
package com.cis.cmp.ui.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.icon_down
import cmp.composeapp.generated.resources.icon_eye_open
import cmp.composeapp.generated.resources.icon_eye_slash
import cmp.composeapp.generated.resources.icon_lang
import cmp.composeapp.generated.resources.img_bar_one_gold
import cmp.composeapp.generated.resources.img_sign_in_bg
import com.cis.cmp.core.theme.Blue53
import com.cis.cmp.core.theme.PrimaryBlue
import com.cis.cmp.core.theme.White
import com.cis.cmp.core.common.InputTextField
import com.cis.cmp.core.common.MorphingButton
import com.cis.cmp.core.common.TextMedium
import com.cis.cmp.core.common.TextSmall
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    navController: NavController,
    viewmodel: LoginViewModel = koinInject()
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var passwordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val loginUiState by viewmodel.uiState.collectAsState()
    val loginEvent by viewmodel.events.collectAsState()

    // Handle login events
    LaunchedEffect(loginEvent) {
        when (loginEvent) {
            is LoginEvent.LoginSuccess -> {
                viewmodel.clearEvent()
                // Navigate to home screen
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is LoginEvent.ShowError -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = (loginEvent as LoginEvent.ShowError).message,
                        actionLabel = "Dismiss"
                    )
                }
                viewmodel.clearEvent()
                // Error is already shown in UI via uiState.error
            }
            null -> {}
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), content = {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(Res.drawable.img_sign_in_bg),
                contentDescription = "img_sign_in_bg",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            // Language selector
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .border(1.dp, Color.White.copy(0.2f), RoundedCornerShape(20.dp))
                    .background(Color.White.copy(0.2f), RoundedCornerShape(20.dp))
                    .clickable { showDialog = true }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(painterResource(Res.drawable.icon_lang), contentDescription = null)
                TextSmall(selectedLanguage.uppercase(), color = Color.White)
                Image(painterResource(Res.drawable.icon_down), contentDescription = null)
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
            ) {
                // Logo on top center
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        painter = painterResource(Res.drawable.img_bar_one_gold),
                        contentDescription = "",
                        modifier = Modifier
                            .width(200.dp)
                            .height(107.dp),
                        contentScale = ContentScale.Fit,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Form fields
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            White.copy(0.5f),
                            RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                        )
                        .padding(vertical = 20.dp, horizontal = 16.dp)
                ) {
                    TextMedium(
                        "Sign in",
                        modifier = Modifier.fillMaxWidth(),
                        size = 24
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    TextSmall(
                        "Hello, welcome back to account",
                        color = Blue53,
                        modifier = Modifier.fillMaxWidth(),
                        size = 14
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    // Email Field
                    InputTextField(
                        value = loginUiState.email,
                        onValueChange = { viewmodel.onEmailChange(it) },
                        placeholder = "Email address",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        fieldTitle = "Email address",
                        isError = loginUiState.emailError != null,
                        errorText = loginUiState.emailError ?: ""
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Password Field
                    InputTextField(
                        value = loginUiState.password,
                        onValueChange = { viewmodel.onPasswordChange(it) },
                        trailing = {
                            Icon(
                                painter = if (passwordVisible)
                                    painterResource(resource = Res.drawable.icon_eye_slash)
                                else
                                    painterResource(resource = Res.drawable.icon_eye_open),
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                modifier = Modifier.clickable {
                                    passwordVisible = !passwordVisible
                                }
                            )
                        },
                        placeholder = "Password",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        fieldTitle = "Password",
                        isError = loginUiState.passwordError != null,
                        errorText = loginUiState.passwordError ?: ""
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    loginUiState.generalError?.let { error ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.Red.copy(alpha = 0.1f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp)
                        ) {
                            TextSmall(
                                text = error,
                                color = Color.Red
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                    // Remember Me + Forgot Password
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = loginUiState.rememberMe,
                                onCheckedChange = { viewmodel.updateIsRemember(it) },
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = White,
                                    checkedColor = PrimaryBlue
                                )
                            )
                            TextMedium("Remember Me")
                        }

                        TextSmall(
                            text = "Forgot Password?",
                            color = PrimaryBlue,
                            modifier = Modifier.clickable {
                                // Navigate to forgot password
//                            navController.navigate("forgot_password")
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Sign In Button
                    MorphingButton(
                        text = "Sign in",
                        isLoading = loginUiState.isLoading,
                        onClick = {
                            focusManager.clearFocus()
                            viewmodel.login()
                        }
                    )

                    Spacer(modifier = Modifier.height(49.dp))

                    // Bottom Text - Sign Up
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextMedium("Don't have an account? ")
                        TextMedium(
                            text = "Sign up here",
                            color = PrimaryBlue,
                            modifier = Modifier.clickable {
                                // Navigate to sign up
                                navController.navigate("signup")
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    })
}
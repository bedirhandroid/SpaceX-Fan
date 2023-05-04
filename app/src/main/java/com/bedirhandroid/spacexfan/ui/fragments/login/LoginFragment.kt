package com.bedirhandroid.spacexfan.ui.fragments.login

import android.widget.Toast
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.base.BaseFragment
import com.bedirhandroid.spacexfan.databinding.FragmentLoginBinding
import com.bedirhandroid.spacexfan.util.gone
import com.bedirhandroid.spacexfan.util.isNotEmpty
import com.bedirhandroid.spacexfan.util.navigateTo
import com.bedirhandroid.spacexfan.util.visible


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override fun initView() {
        viewModel.auth.currentUser?.let {
            navigateTo(R.id.action_loginFragment_to_rocketsFragment)
        }?: kotlin.run {
            setLoginView()
        }
    }

    override fun initListeners() {
        viewBindingScope {
            registerAction.setOnClickListener {
                setRegisterView()
            }
            loginAction.setOnClickListener {
                setLoginView()
            }
            btnLogin.setOnClickListener {
                if (edtEmail.isNotEmpty() && edtPassword.isNotEmpty()) {
                    viewModel.loginUser(edtEmail.text.toString(), edtPassword.text.toString())
                } else {
                    Toast.makeText(requireContext(), "Tüm Alanları Doldurunuz!", Toast.LENGTH_SHORT).show()
                }
            }
            btnRegister.setOnClickListener {
                if (edtEmail.isNotEmpty() && edtUsername.isNotEmpty() && edtPassword.isNotEmpty()) {
                    viewModel.registerUser(edtEmail.text.toString(), edtPassword.text.toString())
                } else {
                    Toast.makeText(requireContext(), "Tüm Alanları Doldurunuz!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initObservers() {
        viewModelScope {
            registerUserMutableLiveData.observe(viewLifecycleOwner) {
                navigateTo(R.id.action_loginFragment_to_rocketsFragment)
            }
            loginUserMutableLiveData.observe(viewLifecycleOwner) {
                navigateTo(R.id.action_loginFragment_to_rocketsFragment)
            }
        }
    }

    private fun setRegisterView() {
        viewBindingScope {
            edtUsername.visible()
            btnRegister.visible()
            loginAction.visible()
            btnLogin.gone()
            registerAction.gone()
            forgotPassword.gone()
        }
    }

    private fun setLoginView() {
        viewBindingScope {
            edtUsername.gone()
            btnRegister.gone()

            loginAction.gone()
            btnLogin.visible()
            registerAction.visible()
            forgotPassword.visible()
        }
    }
}
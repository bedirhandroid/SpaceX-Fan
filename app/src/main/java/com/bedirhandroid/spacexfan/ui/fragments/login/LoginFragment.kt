package com.bedirhandroid.spacexfan.ui.fragments.login

import android.content.Intent
import android.widget.Toast
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.base.BaseFragment
import com.bedirhandroid.spacexfan.databinding.FragmentLoginBinding
import com.bedirhandroid.spacexfan.ui.activities.navdrawer.NavDrawerActivity
import com.bedirhandroid.spacexfan.util.gone
import com.bedirhandroid.spacexfan.util.isNotEmpty
import com.bedirhandroid.spacexfan.util.navigateTo
import com.bedirhandroid.spacexfan.util.visible


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override fun initView() {
        viewModel.auth.currentUser?.let {
            navigateTo(R.id.action_loginFragment_to_rocketsFragment)
        } ?: kotlin.run {
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
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fill_all_fields),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            btnRegister.setOnClickListener {
                if (edtEmail.isNotEmpty() && edtPassword.isNotEmpty()) {
                    viewModel.registerUser(edtEmail.text.toString(), edtPassword.text.toString())
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fill_all_fields),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun initObservers() {
        viewModelScope {
            registerUserMutableLiveData.observe(viewLifecycleOwner) {
                activityRecreate()
            }
            loginUserMutableLiveData.observe(viewLifecycleOwner) {
                activityRecreate()
            }
        }
    }

    private fun setRegisterView() {
        viewBindingScope {
            loginTitle.text = getString(R.string.register)
            btnRegister.visible()
            loginAction.visible()
            btnLogin.gone()
            registerAction.gone()
        }
    }

    private fun setLoginView() {
        viewBindingScope {
            loginTitle.text = getString(R.string.login)
            btnRegister.gone()
            loginAction.gone()
            btnLogin.visible()
            registerAction.visible()
        }
    }

    private fun activityRecreate() {
        requireActivity().apply {
            Intent(requireActivity(), NavDrawerActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}
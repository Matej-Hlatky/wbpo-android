package me.hlatky.wbpo.ui.user

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import me.hlatky.wbpo.Intents
import me.hlatky.wbpo.MainViewModel
import me.hlatky.wbpo.R
import me.hlatky.wbpo.Route
import me.hlatky.wbpo.databinding.FragmentUserLoginBinding
import me.hlatky.wbpo.ui.showErrorDialog
import me.hlatky.wbpo.ui.showProgressDialog
import me.hlatky.wbpo.util.getLocalizedUserFacingMessage
import me.hlatky.wbpo.util.hideKeyboard

class UserLoginFragment : Fragment() {

    private val viewModel: UserLoginViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentUserLoginBinding

    private var progressDialog: DialogInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUserLoginBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.also {
            it.lifecycleOwner = viewLifecycleOwner
            it.model = viewModel

            it.termsButton.setOnClickListener {
                startActivity(Intent(Intents.showTerms(requireContext())))
            }
        }

        viewModel.also { vm ->
            vm.loginResult.observe(viewLifecycleOwner) { state ->
                state?.fold(
                    onLoadingStart = {
                        view.hideKeyboard()
                        progressDialog = context?.showProgressDialog(R.string.user_login_progress_message)
                    },
                    onLoadingEnd = {
                        progressDialog?.dismiss()
                        progressDialog = null
                    },
                    onSuccess = {
                        activityViewModel.selectRoute(Route.USER_LIST)
                    },
                    onFailure = { error ->
                        context?.showErrorDialog(message = error.getLocalizedUserFacingMessage(resources))
                    }
                )
            }
            vm.registerResult.observe(viewLifecycleOwner) { state ->
                state?.fold(
                    onLoadingStart = {
                        view.hideKeyboard()
                        progressDialog = context?.showProgressDialog(R.string.user_register_progress_message)
                    },
                    onLoadingEnd = {
                        progressDialog?.dismiss()
                        progressDialog = null
                    },
                    onSuccess = {
                        activityViewModel.selectRoute(Route.USER_LIST)
                    },
                    onFailure = { error ->
                        context?.showErrorDialog(message = error.getLocalizedUserFacingMessage(resources))
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        progressDialog?.dismiss()
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserLoginFragment()
    }
}

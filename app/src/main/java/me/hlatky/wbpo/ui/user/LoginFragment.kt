package me.hlatky.wbpo.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import me.hlatky.wbpo.Intents
import me.hlatky.wbpo.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false).also {
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
            // TODO observe states
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}

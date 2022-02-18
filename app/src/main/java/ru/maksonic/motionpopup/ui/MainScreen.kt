package ru.maksonic.motionpopup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.launch
import ru.maksonic.motionpopup.MainScreenViewModel
import ru.maksonic.motionpopup.R
import ru.maksonic.motionpopup.core.App
import ru.maksonic.motionpopup.core.OnBackHandler
import ru.maksonic.motionpopup.databinding.ScreenMainBinding

/**
 * @Author: maksonic on 16.02.2022
 */
class MainScreen : Fragment(), OnBackHandler {

    private var _binding: ScreenMainBinding? = null
    private val binding: ScreenMainBinding
        get() = requireNotNull(_binding)

    private var _adapter: PopUpAdapter? = null
    private val adapter get() = requireNotNull(_adapter)

    private val viewModel: MainScreenViewModel by viewModels { getViewModelFactory() }

    private fun getViewModelFactory(): MainScreenViewModel.MainScreenViewModelFactory =
        (requireActivity().application as App).mainScreenVMFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenMainBinding.inflate(inflater, container, false)
        initAdapter()
        render()
        handleBackPressed()
        return binding.root
    }

    private fun initAdapter() {
        _adapter = PopUpAdapter { currency ->
            with(binding) {
                currencyAbbreviation.text = currency.name
                mainScreen.transitionToState(R.id.firstStart)
                btnSaveKey.isClickable = true
            }
        }
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider_layer)
            ?.let { itemDecorator.setDrawable(it) }
        binding.recyclerPopUp.adapter = adapter
        binding.recyclerPopUp.addItemDecoration(itemDecorator)
    }

    private fun render() {
        binding.mainScreen.setTransitionListener(motionListener)
        initClicks()
        lifecycleScope.launch {
            viewModel.currencyList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitList(it)
                }
        }
    }

    private fun initClicks() {
        with(binding) {
            btnCurrency.setOnClickListener(onClickListener)
            btnSaveKey.setOnClickListener(onClickListener)
            clickableBackground.setOnClickListener(onClickListener)
            btnSaveToPhone.setOnClickListener(onClickListener)
            btnSaveToCloud.setOnClickListener(onClickListener)
            btnSaveToNotes.setOnClickListener(onClickListener)
            btnRateApp.setOnClickListener(onClickListener)
        }
    }

    private val motionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

        override fun onTransitionChange(
            motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float
        ) {
            when (motionLayout?.currentState) {
                R.id.firstStart -> motionLayout.transitionToState(R.id.firstStart)
                R.id.secondStart -> motionLayout.transitionToState(R.id.secondStart)
                R.id.firstEnd -> motionLayout.transitionToState(R.id.firstEnd)
                R.id.secondEnd -> motionLayout.transitionToState(R.id.secondEnd)
            }
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {}

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}
    }

    private val onClickListener = View.OnClickListener { view ->
        with(binding) {
            when (view?.id) {
                btnCurrency.id -> {
                    mainScreen.setTransition(R.id.firstStart, R.id.firstEnd)
                    mainScreen.transitionToState(R.id.firstEnd)
                    recyclerPopUp.scrollToPosition(0)
                    btnSaveKey.isClickable = false
                }
                btnSaveKey.id -> {
                    if (mainScreen.currentState == R.id.secondStart ||
                        mainScreen.currentState == R.id.firstStart
                    ) {
                        mainScreen.transitionToState(R.id.secondEnd)
                    } else {
                        mainScreen.transitionToState(R.id.secondStart)
                    }
                }
                clickableBackground.id -> {
                    mainScreen.transitionToState(R.id.firstStart)
                    btnSaveKey.isClickable = true
                }
                btnSaveToPhone.id -> {
                    mainScreen.transitionToState(R.id.secondStart)
                    Toast.makeText(context, btnSaveToPhone.text, Toast.LENGTH_SHORT).show()
                }
                btnSaveToCloud.id -> {
                    mainScreen.transitionToState(R.id.secondStart)
                    Toast.makeText(context, btnSaveToCloud.text, Toast.LENGTH_SHORT).show()
                }
                btnSaveToNotes.id -> {
                    mainScreen.transitionToState(R.id.secondStart)
                    Toast.makeText(context, btnSaveToNotes.text, Toast.LENGTH_SHORT).show()
                }
                btnRateApp.id -> Toast.makeText(context, "★★★★★", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun handleBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    with(binding) {
                        when (mainScreen.currentState) {
                            R.id.firstEnd -> {
                                mainScreen.transitionToState(R.id.firstStart)
                                btnSaveKey.isClickable = true
                            }
                            R.id.secondEnd -> {
                                mainScreen.transitionToState(R.id.secondStart)
                                btnSaveKey.isClickable = true
                            }
                            else -> requireActivity().finish()
                        }
                    }
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
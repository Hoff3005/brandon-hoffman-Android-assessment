package com.glucode.about_you.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.glucode.about_you.EngineerViewmodel
import com.glucode.about_you.R
import com.glucode.about_you.about.views.ProfileStandardCardView
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.engineers.EngineersFragment.Companion.ENGINEER_NAME_ARGUMENT
import com.glucode.about_you.engineers.models.Engineer

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private val engineerViewModel: EngineerViewmodel by activityViewModels()
    private lateinit var profileView: ProfileStandardCardView

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try {
            profileView.setProfileImage(galleryUri)
            engineerViewModel.updatedSelectedEngineerProfilePicture(galleryUri.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileView = ProfileStandardCardView(requireContext())
        arguments?.getString(ENGINEER_NAME_ARGUMENT)?.let { name ->
            val engineer = engineerViewModel.getEngineerByName(name)
            engineerViewModel.selectedEngineer = engineer
            setupViews(engineerViewModel.selectedEngineer)
        }
    }

    private fun setupViews(engineer: Engineer) {
        binding.container.removeAllViews()
        setUpProfile(engineer)
        setUpQuestions(engineer)
    }

    private fun setUpQuestions(engineer: Engineer) {
        engineer.questions.forEach { question ->
            val questionView = QuestionCardView(requireContext())
            questionView.title = question.questionText
            questionView.answers = question.answerOptions
            questionView.selection = question.answer.index

            binding.container.addView(questionView)
        }
    }

    private fun setUpProfile(engineer: Engineer) {
        profileView.name = engineer.name
        profileView.role = engineer.role
        profileView.stats = engineer.quickStats
        if (engineer.defaultImageName.isNotBlank()) {
            profileView.setProfileImage(engineer.defaultImageName.toUri())
        } else {
            profileView.setProfileImage(R.drawable.ic_person)
        }
        profileView.onProfileImageClicked = {
            galleryLauncher.launch("image/*")
        }
        binding.container.addView(profileView)
    }
}
package com.glucode.about_you.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.glucode.about_you.about.views.ProfileStandardCardView
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.engineers.EngineerViewmodel
import com.glucode.about_you.engineers.EngineersFragment.Companion.ENGINEER_NAME_ARGUMENT
import com.glucode.about_you.engineers.models.Engineer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private val engineerViewModel: EngineerViewmodel by activityViewModels()
    private lateinit var profileView: ProfileStandardCardView
    private lateinit var galleryResultContract: GalleryResultContract

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
        galleryResultContract = GalleryResultContract(requireActivity().activityResultRegistry)

        profileView = ProfileStandardCardView(requireContext())
        arguments?.getString(ENGINEER_NAME_ARGUMENT)?.let { name ->
            with(engineerViewModel) {
                val engineer = getEngineerByName(name)
                setSelectedEngineer(engineer)
                setupViews(getSelectedEngineer())
            }
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

    private fun setUpProfile(engineer: Engineer) = with(profileView) {
        name = engineer.name
        role = engineer.role
        stats = engineer.quickStats
        if (engineer.defaultImageName.isNotBlank()) {
            setProfileImage(engineer.defaultImageName.toUri())
        }
        onProfileImageClicked = {
            openGallery()
        }
        binding.container.addView(this)
    }

    private fun openGallery() {
        galleryResultContract.getImageFromGallery()
            .observe(viewLifecycleOwner) {
                it?.let { uri ->
                    profileView.setProfileImage(uri)
                    engineerViewModel.updatedSelectedEngineerProfilePicture(uri.toString())
                }
            }
    }
}
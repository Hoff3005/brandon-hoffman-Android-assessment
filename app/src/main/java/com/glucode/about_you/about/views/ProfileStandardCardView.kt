package com.glucode.about_you.about.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewProfileStandardCardBinding
import com.glucode.about_you.engineers.models.QuickStats

class ProfileStandardCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : CardView(context, attrs, defStyleAttr) {
    private val binding: ViewProfileStandardCardBinding =
        ViewProfileStandardCardBinding.inflate(LayoutInflater.from(context), this)

    var name: String? = null
        set(value) {
            field = value
            binding.name.text = value
        }

    var role: String? = null
        set(value) {
            field = value
            binding.role.text = value
        }

    var stats: QuickStats? = null
        set(value) {
            field = value
            binding.yearsValueTextView.text = value?.years.toString()
            binding.coffeesValueTextView.text = value?.coffees.toString()
            binding.bugsValueTextView.text = value?.bugs.toString()
        }

    var onProfileImageClicked: () -> Unit = { }

    init {
        radius = resources.getDimension(R.dimen.corner_radius_normal)
        elevation = resources.getDimension(R.dimen.elevation_normal)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.black))
        binding.profileStatsCard.radius = resources.getDimension(R.dimen.corner_radius_normal)
        binding.profileImage.setOnClickListener {
            onProfileImageClicked()
        }
    }

    fun setProfileImage(uri: Uri) {
        binding.profileImage.setImageURI(uri)
    }

    fun setProfileImage(imageResourceId: Int) {
        binding.profileImage.setImageResource(imageResourceId)
    }
}
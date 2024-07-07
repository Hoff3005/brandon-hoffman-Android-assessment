package com.glucode.about_you


import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun navigatingToAboutFragmentAndBackUsingBackArrow() {
        onView(isRoot()).perform(waitFor(600))

        val recyclerView = onView(
            allOf(
                withId(R.id.list),
                withParent(
                    allOf(
                        withId(R.id.fragment_host),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val recyclerView2 = onView(
            allOf(
                withId(R.id.list),
                childAtPosition(
                    withId(R.id.fragment_host),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val linearLayoutCompat = onView(
            allOf(
                withId(R.id.container),
                withParent(withParent(withId(R.id.fragment_host))),
                isDisplayed()
            )
        )
        linearLayoutCompat.check(matches(isDisplayed()))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(com.google.android.material.R.id.action_bar),
                        childAtPosition(
                            withId(com.google.android.material.R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val recyclerView3 = onView(
            allOf(
                withId(R.id.list),
                withParent(
                    allOf(
                        withId(R.id.fragment_host),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        recyclerView3.check(matches(isDisplayed()))
    }

    @Test
    fun navigateToEngineerAndDisplayProfileViewWithDetails() {
        onView(isRoot()).perform(waitFor(600))

        val recyclerView = onView(
            allOf(
                withId(R.id.list),
                withParent(
                    allOf(
                        withId(R.id.fragment_host),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val recyclerView2 = onView(
            allOf(
                withId(R.id.list),
                childAtPosition(
                    withId(R.id.fragment_host),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val linearLayoutCompat = onView(
            allOf(
                withId(R.id.container),
                withParent(withParent(withId(R.id.fragment_host))),
                isDisplayed()
            )
        )
        linearLayoutCompat.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.profile_image), withContentDescription("Profile Image"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.name), withText("Reenen"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Reenen")))

        val textView2 = onView(
            allOf(
                withId(R.id.role), withText("Dev manager"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Dev manager")))

        val textView3 = onView(
            allOf(
                withId(R.id.role), withText("Dev manager"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Dev manager")))
    }

    @Test
    fun navigateAndOpenGallery() {
        onView(isRoot()).perform(waitFor(600))

        val recyclerView = onView(
            allOf(
                withId(R.id.list),
                withParent(
                    allOf(
                        withId(R.id.fragment_host),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))

        val recyclerView2 = onView(
            allOf(
                withId(R.id.list),
                childAtPosition(
                    withId(R.id.fragment_host),
                    0
                )
            )
        )
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val linearLayoutCompat = onView(
            allOf(
                withId(R.id.container),
                withParent(withParent(withId(R.id.fragment_host))),
                isDisplayed()
            )
        )
        linearLayoutCompat.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.profile_image), withContentDescription("Profile Image"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.profile_image), withContentDescription("Profile Image"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.glucode.about_you.about.views.ProfileStandardCardView")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )

        val resultData = Intent(Intent.ACTION_GET_CONTENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType("image/*")
            .setData(Uri.parse("android.resource://com.glucode.about_you/drawable/ic_launcher_background"))
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)

        intending(toPackage("com.android.contacts")).respondWith(result)

        appCompatImageView.perform(click())
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

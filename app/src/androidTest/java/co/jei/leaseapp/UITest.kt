package co.jei.leaseapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import co.jei.leaseapp.views.HomeActivity
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UITest {

    @get:Rule
    val homeActivityRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)

}

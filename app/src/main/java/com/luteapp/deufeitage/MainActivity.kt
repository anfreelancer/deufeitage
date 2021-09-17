package com.luteapp.deufeitage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luteapp.deufeitage.model.YearItem
import com.luteapp.deufeitage.ui.theme.DeufeitageTheme
import com.luteapp.deufeitage.utils.DateUtility
import com.luteapp.deufeitage.utils.HolidayCalculator
import com.luteapp.deufeitage.utils.HolidayConfigReader
import com.luteapp.deufeitage.utils.PreferenceManager
import com.luteapp.deufeitage.ui.*

/**
 * MainActivity of the app.
 *
 * @author MicMun
 * @version 1.4, 16.08.21
 */
class MainActivity : ComponentActivity() {
    private var holidayCalculator: HolidayCalculator? = null
    private var holidayConfigReader: HolidayConfigReader? = null
    private var preferenceManager: PreferenceManager? = null
    private var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPreferences()

        setContent {
            Content()
        }
        handler.postDelayed(Runnable {
            SplashActivity.setFirst(applicationContext)
            startActivity(Intent(this@MainActivity, NotPremiumActivity::class.java))
            finish()
        }, 5000)

    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Content()
    }

    @Composable
    fun Content() {
        // Read config and init calculator
        if (holidayConfigReader == null) {
            holidayConfigReader = HolidayConfigReader(LocalContext.current)
            holidayCalculator = HolidayCalculator(holidayConfigReader!!.holidays)
        }

        var selectedYear by remember {
            mutableStateOf(preferenceManager?.getYear() ?: DateUtility.currentYear())
        }
        var stateId by remember {
            mutableStateOf(preferenceManager?.getState() ?: "AA")
        }

        val scrollState = rememberScrollState()

        Scaffold(topBar = {
            Toolbar()
        }) {
            DeufeitageTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .verticalScroll(state = scrollState)
                    ) {
                        // Selection of the german states
                        StateSelector(stateId = stateId, statelistener = { state ->
                            stateId = state.key
                            preferenceManager!!.setState(stateId)
                        })
                        // Selection of the year
                        YearSelector(selectedYear = selectedYear, yearListener = { year ->
                            selectedYear = year.year
                            preferenceManager!!.setYear(selectedYear)
                        })
                        // Display the list of holidays
                        HolidayView(stateId = stateId, selectedYear = selectedYear)
                    }
                }
            }
        }
    }

    @Composable
    fun StateSelector(stateId: String, statelistener: StateSelectedListener) {
        Box(modifier = Modifier.padding(0.dp, 8.dp)) {
            val initState = holidayConfigReader!!.states.find { it.key == stateId }
                ?: holidayConfigReader!!.states[0]
            StateSpinner(
                states = holidayConfigReader!!.states,
                initValue = initState,
                listener = statelistener
            )
        }
    }

    @Composable
    fun YearSelector(selectedYear: Int, yearListener: YearSelectedListener) {
        Box {
            val years = calcYearList()
            val initYear = years.find { it.year == selectedYear } ?: years[0]
            YearSelector(years = years, initValue = initYear, listener = yearListener)
        }
    }

    @Composable
    fun HolidayView(stateId: String, selectedYear: Int) {
        Box(modifier = Modifier.padding(0.dp, 8.dp)) {
            val state = holidayConfigReader!!.states.find { it.key == stateId }
                ?: holidayConfigReader!!.states[0]
            HolidayListView(holidays = holidayCalculator!!.getHolidays(selectedYear, state))
        }
    }

    /**
     * Returns the list of years for year selector.
     *
     * @return list of years for year selector.
     */
    private fun calcYearList(): List<YearItem> {
        val years = mutableListOf<YearItem>()
        val currentYear = DateUtility.currentYear()

        for (i in currentYear - 4..currentYear + 4) {
            years.add(YearItem(i))
        }

        return years
    }

    /**
     * Inits the preferences.
     */
    private fun initPreferences() {
        val sharedPreference =
            getSharedPreferences(PreferenceManager.pref_name, Context.MODE_PRIVATE)
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        val version = pInfo.versionName

        preferenceManager = PreferenceManager(sharedPreference, version)
    }
}

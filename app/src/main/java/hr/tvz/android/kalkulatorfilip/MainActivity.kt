package hr.tvz.android.kalkulatorfilip

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.Spinner
import android.widget.ToggleButton
import hr.tvz.android.kalkulatorfilip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var currentLanguage: String
    private var total: Double = 0.0
    private var calculated: Boolean = false
    private var isError: Boolean = false
    lateinit var themeToggle: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Light)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentLanguage = resources.getString(R.string.default_language)

        setLanguage(currentLanguage)

        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this

        binding.button.setOnClickListener {
            val distance: Double = if (binding.distanceInput.text.toString().trim().isNotEmpty()) binding.distanceInput.text.toString().toDouble() else 0.0
            val gasUsage: Double = if (binding.fuelUsageInput.text.toString().trim().isNotEmpty()) binding.fuelUsageInput.text.toString().toDouble() else 0.0
            val gasPrice: Double = if (binding.fuelPriceInput.text.toString().trim().isNotEmpty()) binding.fuelPriceInput.text.toString().toDouble() else 0.0

            if (distance > 0 && gasUsage > 0 && gasPrice > 0) {
                isError = false
                total = (distance / 100) * gasUsage * gasPrice
                calculated = true

                setResult(currentLanguage, total)
            } else {
                isError = true
                setInvalidFieldsText()
            }
        }

        themeToggle = findViewById(R.id.darkModeToggle)
        themeToggle.setOnCheckedChangeListener(this)

        setDarkModeText()

    }

    fun setDarkModeText() {
        when (currentLanguage) {
            resources.getString(R.string.lang_hr) -> {
                binding.darkModeToggle.text = resources.getString(R.string.dark_mode_text_hr)
                binding.darkModeToggle.textOff = resources.getString(R.string.dark_mode_text_hr)
                binding.darkModeToggle.textOn = resources.getString(R.string.dark_mode_text_hr)
            }
            resources.getString(R.string.lang_en) -> {
                binding.darkModeToggle.text = resources.getString(R.string.dark_mode_text_en)
                binding.darkModeToggle.textOff = resources.getString(R.string.dark_mode_text_en)
                binding.darkModeToggle.textOn = resources.getString(R.string.dark_mode_text_en)
            }
            resources.getString(R.string.lang_es) -> {
                binding.darkModeToggle.text = resources.getString(R.string.dark_mode_text_es)
                binding.darkModeToggle.textOff = resources.getString(R.string.dark_mode_text_es)
                binding.darkModeToggle.textOn = resources.getString(R.string.dark_mode_text_es)
            }
        }
    }

    fun setInvalidFieldsText() {
        when (currentLanguage) {
            resources.getString(R.string.lang_hr) -> {
                binding.result.setText(resources.getString(R.string.result_error_hr))
            }
            resources.getString(R.string.lang_en) -> {
                binding.result.setText(resources.getString(R.string.result_error_en))
            }
            resources.getString(R.string.lang_es) -> {
                binding.result.setText(resources.getString(R.string.result_error_es))
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        setLanguage(resources.getString(R.string.default_language))
        setDarkModeText()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            setLanguage(parent.getItemAtPosition(position).toString())
            if (calculated) {
                setResult(currentLanguage, total)
            }
            if (isError) {
                setInvalidFieldsText()
            }
            setDarkModeText()
        }
    }

    fun setLanguage(language: String) {
        when (language) {
            resources.getString(R.string.lang_hr) -> {
                currentLanguage = resources.getString(R.string.lang_hr)
                binding.appTitle.setText(resources.getString(R.string.app_title_hr))
                        binding.distanceText.setText(resources.getString(R.string.distance_hr))
                        binding.gasUsageText.setText(resources.getString(R.string.gas_usage_hr))
                        binding.gasPriceText.setText(resources.getString(R.string.gas_price_hr))
                        binding.languageText.setText(resources.getString(R.string.language_hr))
                        binding.button.setText(resources.getString(R.string.button_hr))
            }
            resources.getString(R.string.lang_en) -> {
                currentLanguage = resources.getString(R.string.lang_en)
                binding.appTitle.setText(resources.getString(R.string.app_title_en))
                        binding.distanceText.setText(resources.getString(R.string.distance_en))
                        binding.gasUsageText.setText(resources.getString(R.string.gas_usage_en))
                        binding.gasPriceText.setText(resources.getString(R.string.gas_price_en))
                        binding.languageText.setText(resources.getString(R.string.language_en))
                        binding.button.setText(resources.getString(R.string.button_en))
            }
            resources.getString(R.string.lang_es) -> {
                currentLanguage = resources.getString(R.string.lang_es)
                binding.appTitle.setText(resources.getString(R.string.app_title_es))
                        binding.distanceText.setText(resources.getString(R.string.distance_es))
                        binding.gasUsageText.setText(resources.getString(R.string.gas_usage_es))
                        binding.gasPriceText.setText(resources.getString(R.string.gas_price_es))
                        binding.languageText.setText(resources.getString(R.string.language_es))
                        binding.button.setText(resources.getString(R.string.button_es))
            }
            else -> setLanguage(resources.getString(R.string.default_language))
        }
    }

    fun setResult(language: String, total: Double) {
        var formatString = resources.getString(R.string.result_format_string)
        when (language) {
            resources.getString(R.string.lang_hr) -> {
                binding.result.text = resources.getString(R.string.trip_price_hr) + formatString.format(total).toString() + resources.getString(R.string.default_currency_code)
            }
            resources.getString(R.string.lang_en) -> {
                binding.result.text = resources.getString(R.string.trip_price_en) + formatString.format(total).toString() + resources.getString(R.string.default_currency_code)
            }
            resources.getString(R.string.lang_es) -> {
                binding.result.text = resources.getString(R.string.trip_price_es) + formatString.format(total).toString() + resources.getString(R.string.default_currency_code)
            }
            else -> setResult(resources.getString(R.string.default_language), total)
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        var colorWhite = resources.getString(R.string.color_white)
        var colorBlack = resources.getString(R.string.color_black)
        if (isChecked) {
            setColor(colorWhite, colorBlack)
        } else {
            setColor(colorBlack, colorWhite)
        }
    }

    fun setColor(textColor: String, backgroundColor: String) {
        binding.appTitle.setTextColor(Color.parseColor(textColor))
        binding.distanceText.setTextColor(Color.parseColor(textColor))
        binding.gasUsageText.setTextColor(Color.parseColor(textColor))
        binding.gasPriceText.setTextColor(Color.parseColor(textColor))
        binding.languageText.setTextColor(Color.parseColor(textColor))
        binding.result.setTextColor(Color.parseColor(textColor))
        binding.root.setBackgroundColor(Color.parseColor(backgroundColor))
    }
}
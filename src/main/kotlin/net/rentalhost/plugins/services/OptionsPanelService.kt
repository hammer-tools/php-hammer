package net.rentalhost.plugins.services

import net.miginfocom.swing.MigLayout
import java.util.function.Consumer
import javax.swing.ButtonGroup
import javax.swing.JPanel
import javax.swing.JRadioButton

class OptionsPanelService private constructor() {
    private val optionsPanel: JPanel = JPanel(MigLayout("fillx"))

    fun delegateRadioCreation(delegate: Consumer<RadioComponent>) {
        val radioComponent = RadioComponent()

        delegate.accept(radioComponent)

        if (radioComponent.selectedOption == null && radioComponent.radioOptions.size >= 1) {
            val firstWidget = radioComponent.radioOptions[0]
            firstWidget.radioButton.isSelected = true
            firstWidget.updateConsumer.accept(true)
        }
    }

    inner class RadioComponent {
        internal val radioOptions: MutableList<RadioOption> = ArrayList()

        private val buttonGroup = ButtonGroup()

        internal var selectedOption: RadioOption? = null

        fun addOption(
            label: String,
            defaultValue: Boolean,
            updateConsumer: Consumer<Boolean>
        ) {
            val newOption = RadioOption(label, defaultValue, updateConsumer)

            radioOptions.add(newOption)

            if (defaultValue) {
                selectedOption = newOption
            }
        }

        inner class RadioOption internal constructor(
            label: String,
            defaultValue: Boolean,
            val updateConsumer: Consumer<Boolean>
        ) {
            val radioButton: JRadioButton

            init {
                radioButton = JRadioButton(label, defaultValue)
                radioButton.addItemListener {
                    val isSelected = radioButton.isSelected

                    if (selectedOption != null &&
                        selectedOption != this) {
                        selectedOption!!.updateConsumer.accept(false)
                    }

                    updateConsumer.accept(isSelected)

                    selectedOption = if (isSelected) this else null
                }

                optionsPanel.add(radioButton, "wrap")
                buttonGroup.add(radioButton)
            }
        }
    }

    companion object {
        fun create(delegateBuilder: Consumer<OptionsPanelService>): JPanel {
            val component = OptionsPanelService()

            delegateBuilder.accept(component)

            return component.optionsPanel
        }
    }
}

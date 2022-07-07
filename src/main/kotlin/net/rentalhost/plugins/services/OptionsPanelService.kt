package net.rentalhost.plugins.services

import net.miginfocom.swing.MigLayout
import java.util.function.Consumer
import javax.swing.ButtonGroup
import javax.swing.JCheckBox
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

    fun addCheckbox(text: String, selected: Boolean, updateConsumer: Consumer<Boolean>) {
        optionsPanel.add(with(JCheckBox(text, selected)) {
            addItemListener { updateConsumer.accept(isSelected) }
            this
        }, "wrap")
    }

    inner class RadioComponent {
        internal val radioOptions: MutableList<RadioOption> = ArrayList()

        private val buttonGroup = ButtonGroup()

        internal var selectedOption: RadioOption? = null

        fun addOption(text: String, selected: Boolean, updateConsumer: Consumer<Boolean>) {
            val newOption = RadioOption(text, selected, updateConsumer)

            radioOptions.add(newOption)

            if (selected) {
                selectedOption = newOption
            }
        }

        inner class RadioOption internal constructor(text: String, selected: Boolean, val updateConsumer: Consumer<Boolean>) {
            val radioButton: JRadioButton

            init {
                radioButton = JRadioButton(text, selected)
                radioButton.addItemListener {
                    val isSelected = radioButton.isSelected

                    if (selectedOption != null &&
                        selectedOption != this) {
                        (selectedOption ?: return@addItemListener).updateConsumer.accept(false)
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

package net.rentalhost.plugins.php.hammer.services;

import net.miginfocom.swing.MigLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class OptionsPanelService {
    @NotNull
    private final JPanel optionsPanel;

    @NotNull
    public static JPanel create(@NotNull final Consumer<OptionsPanelService> delegateBuilder) {
        final OptionsPanelService component = new OptionsPanelService();

        delegateBuilder.accept(component);

        return component.optionsPanel;
    }

    private OptionsPanelService() {
        optionsPanel = new JPanel(new MigLayout("fillx"));
    }

    public void delegateRadioCreation(@NotNull final Consumer<RadioComponent> delegate) {
        final RadioComponent radioComponent = new RadioComponent();

        delegate.accept(radioComponent);

        if ((radioComponent.selectedOption == null) &&
            (radioComponent.radioOptions.size() >= 1)) {
            final RadioComponent.RadioOption firstWidget = radioComponent.radioOptions.get(0);
            firstWidget.radioButton.setSelected(true);
            firstWidget.updateConsumer.accept(true);
        }
    }

    public class RadioComponent {
        List<RadioOption> radioOptions = new ArrayList<>();
        private final ButtonGroup buttonGroup = new ButtonGroup();

        @Nullable
        private RadioOption selectedOption;

        public void addOption(
            @NotNull final String label,
            final boolean defaultValue,
            @NotNull final Consumer<Boolean> updateConsumer
        ) {
            final RadioOption newOption = new RadioOption(label, defaultValue, updateConsumer);
            radioOptions.add(newOption);

            if (defaultValue) {
                selectedOption = newOption;
            }
        }

        private class RadioOption {
            @NotNull final JRadioButton radioButton;

            @NotNull final Consumer<Boolean> updateConsumer;

            RadioOption(
                @NotNull final String label,
                final boolean defaultValue,
                @NotNull final Consumer<Boolean> updateConsumer
            ) {
                this.updateConsumer = updateConsumer;

                radioButton = new JRadioButton(label, defaultValue);
                radioButton.addItemListener((itemEvent) -> {
                    final boolean isSelected = radioButton.isSelected();

                    if ((selectedOption != null) &&
                        !Objects.equals(selectedOption, this)) {
                        selectedOption.updateConsumer.accept(false);
                    }

                    updateConsumer.accept(isSelected);
                    selectedOption = isSelected ? this : null;
                });

                optionsPanel.add(radioButton, "wrap");
                buttonGroup.add(radioButton);
            }
        }
    }
}

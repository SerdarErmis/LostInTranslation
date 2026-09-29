package translation;

import javax.swing.*;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();

            java.util.List<String> countryCodes = translator.getCountryCodes();
            java.util.List<String> languageCodes = translator.getLanguageCodes();

            String[] countryNames = new String[countryCodes.size()];
            for (int i = 0; i < countryCodes.size(); i++) {
                countryNames[i] = countryConverter.fromCountryCode(countryCodes.get(i));
            }

            String[] languageNames = new String[languageCodes.size()];
            for (int i = 0; i < languageCodes.size(); i++) {
                languageNames[i] = languageConverter.fromLanguageCode(languageCodes.get(i));
            }

            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));

            JList<String> countryList = new JList<>(countryNames);
            JScrollPane scrollPane = new JScrollPane(countryList);
            scrollPane.setPreferredSize(new java.awt.Dimension(200, 150));
            countryPanel.add(scrollPane);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageBox = new JComboBox<>(languageNames);
            languagePanel.add(languageBox);

            JLabel resultLabel = new JLabel("Translation: ");

            countryList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && countryList.getSelectedIndex() >= 0) {
                    int countryIndex = countryList.getSelectedIndex();
                    int languageIndex = languageBox.getSelectedIndex();
                    String result = translator.translate(countryCodes.get(countryIndex), languageCodes.get(languageIndex));
                    resultLabel.setText("Translation: " + result);
                }
            });

            languageBox.addActionListener(e -> {
                if (countryList.getSelectedIndex() >= 0) {
                    int countryIndex = countryList.getSelectedIndex();
                    int languageIndex = languageBox.getSelectedIndex();
                    String result = translator.translate(countryCodes.get(countryIndex), languageCodes.get(languageIndex));
                    resultLabel.setText("Translation: " + result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(resultLabel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}

package com.example.wordoccurence;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;





    public class FinalGUI extends JFrame {


        /**
         * The method below  is creating a new object named testing with a URL that is pointing to a webpage
         * Then nextly, creating a string variable that will be tested for in our word occurence application.
         * Next we convert string to bytes.
         *The final GUI class also extends the Jframe class which is a container window in java swing.
         *
         * @throws Exception
         */
        @Test
        public void testWordOccurrence() throws Exception {

            URL TESTING = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");


            String TestWords = "once";


            InputStream TestInput = new ByteArrayInputStream(TestWords.getBytes());


            BufferedReader TestReader = new BufferedReader(new InputStreamReader(TestInput));


            FinalGUI TestGUI = new FinalGUI() {

                URL newURL () throws Exception {
                    return TESTING;
                }

                BufferedReader createBufferedReader(URL url)  {
                    return TestReader;
                }


                Scanner newScanner (InputStream inputStream) {
                    return new Scanner(inputStream);
                }

            };


            TestGUI.WordOccurence();


            String outputText = TestGUI.getOutputText().getText();


            StringBuilder OUTPUT = new StringBuilder();

          OUTPUT.append("once occurred 1 times");



            Assertions.assertEquals(OUTPUT.toString(), outputText);
        }


        /**
         * A public JtextArea variable is created as outputtext that will be showing the output of our word occurences.
         * @return
         */


        public JTextArea getOutputText() {
            return outputtext;
        }




        private JTextArea outputtext;

        /**
         * Below are GUI components which describes the title of the word occurence, basicallt setting the title of the window
         * and also configuring some default settings
         * Also, setting the layout and the borders of the window
         * Then JScroll pane is created and output text is added to it.
         * The Jscroll pane is obviously used for scrolling so complete output can br shoen.
         * Nextly, Jbutton has been created and has been labeled "show output"
         * so once the button is clicked output will br shown.
         * A button panel is added to the final gui frame, using borderlayout.south that will place the button at the bottom of the frame
         *
         */

        public FinalGUI() {
            setTitle("Word Occurrence");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            outputtext = new JTextArea();
            outputtext.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(outputtext);
            add(scrollPane, BorderLayout.CENTER);

            JButton showOutputButton = new JButton("Show Output");
            showOutputButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        WordOccurence();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


            JPanel buttonPanel = new JPanel();
            buttonPanel.add(showOutputButton);
            add(buttonPanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);

        }

        /**
         *  Here wordoccurence method is defined. it will read the content of the URL wit the help of the buffer reader.
         *  Then it points to a webpage that will read the text.it is done so by opening the input stream and using the inputsteamreader.
         *  then after reading the content the input stream closes.
         *  Then we create a method to modify and replace all punctuation with blank spaces.
         *  Then there are two array lists. words and count and it will store words and how many times they have occured
         *  Then scanner will read each words and if the word is in the list, it will increment by 1.
         *  after counting the words thoroughly, scanner and input stream is closes.
         *
         * @throws Exception
         */
        public void WordOccurence() throws Exception {
            URL url = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append("\n");
            }
            in.close();

            String modifiedContent = content.toString().replace("<[^]+>", "")
                    .replaceAll("&[^;]+;", "")
                    .replaceAll("<br>", "")
                    .replaceAll("<b>", "")
                    .replaceAll("</b>", "")
                    .replaceAll("<i>", "")
                    .replaceAll("</i>", "")
                    .replaceAll("<u>", "")
                    .replaceAll("</u>", "")
                    .replaceAll("\"", "")
                    .replaceAll("<.*?>", "")
                    .replaceAll("=", "")
                    .replaceAll(",", "")
                    .replaceAll("\\[|\\]", "")
                    .replaceAll("\"", "")
                    .replaceAll(",", "")
                    .replace("</style>", "")
                    .replaceAll("}", "")
                    .replaceAll("<.*?>", "")
                    .replaceAll("\\.", "");

            InputStream inputStream = url.openStream();
            Scanner urlInput = new Scanner(inputStream);

            ArrayList<String> words = new ArrayList<>();
            ArrayList<Integer> count = new ArrayList<>();


            /**
             *  The to 20 words and their counts are then assigned to the output using a whole loop
             *
             */

            int start1 = 235;
            int output1 = 0;

            while (urlInput.hasNext() && output1 < start1 + count.size()) {
                String nextWord = urlInput.next().toLowerCase();
                if (words.contains(nextWord)) {
                    int index = words.indexOf(nextWord);
                    count.set(index, count.get(index) + 1);
                } else {

                    words.add(nextWord);
                    count.add(1);
                }
            }

            urlInput.close();
            inputStream.close();

            StringBuilder output = new StringBuilder();
            for (int i = start1; i < words.size() && i < start1 + 20; i++) {
                output.append(words.get(i)).append(" occurred ").append(count.get(i)).append(" times\n");
            }
/**
 *  the output.tostring is set as output text which then disaplys the final output in the GUI.
 *
 */
            outputtext.setText(output.toString());

        }

        /**
         * getwords and getcounts are two methods that take the words, counts them and displays them.v
         * @return
         */
        public ArrayList<String> getWords() {
            return extractWords(outputtext.getText());
        }

        public ArrayList<Integer> getCounts() {
            return extractCounts(outputtext.getText());
        }


        /**
         * below we have two different methods, one taking the output text as input and then returning
         Arraylist of string that shows the words.  Firdt methof is used for wrods and the other methods
         is used for integers.
         Then we have main function which we know is the entry point for any progrsm.
         * @param output
         * @return
         */
        private ArrayList<String> extractWords(String output) {
            ArrayList<String> words = new ArrayList<>();
            String[] lines = output.split("\n");
            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String word = parts[0];
                    words.add(word);
                }
            }
            return words;
        }

        private ArrayList<Integer> extractCounts(String output) {
            ArrayList<Integer> counts = new ArrayList<>();
            String[] lines = output.split("\n");
            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    int count = Integer.parseInt(parts[2]);
                    counts.add(count);
                }
            }
            return counts;
        }


        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {


                public void run() {
                    FinalGUI wordOccurrenceGUI = new FinalGUI();
                    wordOccurrenceGUI.setVisible(true);
                }
            });



        }
    }





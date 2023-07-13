package com.hust.quiz.Controllers;

import com.hust.quiz.Models.Question;
import com.hust.quiz.Models.Choice;
import com.hust.quiz.Services.ChoiceService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//hiển thị câu hỏi trong bài test
public class QuestionInStartController implements Initializable {
    @FXML
    private ToggleGroup Choice;

    @FXML
    private Label label_questionContent;

    @FXML
    private Label label_questionNum;

    @FXML
    private RadioButton rButton_A;

    @FXML
    private RadioButton rButton_B;

    @FXML
    private RadioButton rButton_C;

    @FXML
    private RadioButton rButton_D;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setInforQuestion(Question question, int questiomNum) {
        this.label_questionNum.setText(String.valueOf(questiomNum));
        this.label_questionContent.setText(question.getQuestion_text());
        List<Choice> listChoice = new ArrayList<>();
        listChoice.addAll(ChoiceService.getChoice(question.getQuestion_id()));
        int numChoice = listChoice.size();
        if (!listChoice.isEmpty()) {
            if(numChoice == 1){
                rButton_A.setText(listChoice.get(0).getContent());
            }else if( numChoice == 2){
                rButton_A.setText(listChoice.get(0).getContent());
                rButton_B.setText(listChoice.get(1).getContent());
            }else if(numChoice == 3){
                rButton_A.setText(listChoice.get(0).getContent());
                rButton_B.setText(listChoice.get(1).getContent());
                rButton_C.setText(listChoice.get(2).getContent());
            }else if(numChoice == 4){
                rButton_A.setText(listChoice.get(0).getContent());
                rButton_B.setText(listChoice.get(1).getContent());
                rButton_C.setText(listChoice.get(2).getContent());
                rButton_D.setText(listChoice.get(3).getContent());
            }
        }
    }
}
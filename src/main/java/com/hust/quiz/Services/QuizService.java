package com.hust.quiz.Services;

import com.hust.quiz.Models.Question;
import com.hust.quiz.Models.Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QuizService {
    public static void addQuiz(Quiz quiz) {
        try (Connection conn = Utils.getConnection()) {
            String sql = "INSERT INTO quiz (quiz_name, quiz_description, quiz_time_limit, quiz_time_format, " +
                    "quiz_open_date, quiz_close_date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, quiz.getQuiz_name());
            pst.setString(2, quiz.getQuiz_description());
            pst.setInt(3, quiz.getTimeLimit());
            pst.setString(4, quiz.getTimeFormat());
            pst.setDate(5, quiz.getOpen_date());
            pst.setDate(6, quiz.getClose_date());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //lấy danh sách câu hỏi của quiz
    public static List<Question> getQuestionQuiz(int quiz_id) {
        List<Question> result = new ArrayList<>();
        try (Connection conn = Utils.getConnection()) {
            String sql = "SELECT * FROM question JOIN quiz_question ON question.question_id = quiz_question.question_id WHERE quiz_question.quiz_id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(quiz_id));

            // execute
            ResultSet rs = stmt.executeQuery();

            // add questions found to list
            while (rs.next()) {
                Question question = new Question(rs.getInt("question_id"), rs.getString("question_name"),
                        rs.getString("question_text"), rs.getString("question_image"), 1, rs.getInt("category_id"));
                result.add(question);
            }
            // close
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static Quiz getQuiz(String quiz_name) {
        try (Connection conn = Utils.getConnection()) {
            // write query
            String sql = "SELECT * FROM quiz WHERE quiz_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, quiz_name);

            // execute
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LocalDate open_date;
                LocalDate close_date;
                if (rs.getDate("quiz_open_date") == null) {
                    open_date = null;
                } else {
                    open_date = rs.getDate("quiz_open_date").toLocalDate();
                }
                if (rs.getDate("quiz_close_date") == null) {
                    close_date = null;
                } else {
                    close_date = rs.getDate("quiz_close_date").toLocalDate();
                }
                return new Quiz(rs.getInt("quiz_id"), rs.getString("quiz_name"), rs.getString("quiz_description"),
                        rs.getInt("quiz_time_limit"), rs.getString("quiz_time_format"),
                        open_date, close_date);
            } else {
                System.out.println("No Quiz found");
            }

            // close
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getAllQuiz() {
        List<String> result = new ArrayList<>();
        try (Connection conn = Utils.getConnection()) {
            // write query
            String sql = "SELECT quiz_name FROM quiz";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // execute
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("quiz_name"));
            }
            // close
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static void updateQuestionQuiz(int quizID, List<Question> questionToAdd, List<Question> questionToDelete) {
        try (Connection conn = Utils.getConnection()) {
            String sql = "INSERT INTO `quiz_question` (`quiz_id`, `question_id`, `question_order`) VALUES (?, ?, ?);";
            PreparedStatement pst = conn.prepareStatement(sql);
            int order = quizCountQues(quizID);
            for (Question question : questionToAdd) {
                pst.setString(1, String.valueOf(quizID));
                pst.setString(2, String.valueOf(question.getQuestion_id()));
                pst.setString(3, String.valueOf(order));
                pst.executeUpdate();
                order = order + 1;
            }
            pst.close();

            String sql2 = "DELETE FROM `quiz_question` WHERE `quiz_id` = ? AND `question_id` = ?;";
            PreparedStatement pst2 = conn.prepareStatement(sql2);
            for (Question question : questionToDelete) {
                pst2.setString(1, String.valueOf(quizID));
                pst2.setString(2, String.valueOf(question.getQuestion_id()));
                pst2.executeUpdate();
            }
            pst2.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static int quizCountQues(int quizID) {
        List<Question> questionList = getQuestionQuiz(quizID);
        return questionList.size();
    }
}

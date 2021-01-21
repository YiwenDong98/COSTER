package org.usask.srlab.coster.utils;

import com.opencsv.CSVWriter;
import org.usask.srlab.coster.model.TestResult;
import org.usask.srlab.coster.model.OLDEntry;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EvaluationUtil {
    double precision;
    double recall;
    double fscore;

    public EvaluationUtil(List<TestResult> results){
        CSVWriter writer = null;
        String snRBenchOutPath = System.getenv("coster_stat_path");
        if (snRBenchOutPath != null) {
            try {
                writer = new CSVWriter(new FileWriter(snRBenchOutPath), ',', '"', '\\', "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            long tp = 0;
        long fp = 0;
        for (TestResult result : results) {
            String actualFQN = result.getApiElement().getActualFQN();

            System.out.println("For API Element: " + result.getApiElement().toString());
            System.out.println("Rec FQN: " + result.getRecommendations().stream().map(OLDEntry::getFqn).collect(Collectors.toList()));
            if (writer != null) {
                writer.writeNext(new String[]{
                        Paths.get(result.getApiElement().getFileName()).getFileName().toString(),
                        String.valueOf(result.getApiElement().getLineNumber()),
                        result.getApiElement().getActualFQN(),
                        String.valueOf(result.getApiElement().getAstNode().getStartPosition()),
                        String.valueOf(result.getApiElement().getAstNode().getLength()),
                        result.getApiElement().getAstNode().toString()});
            }

//            Set<String> predictedFQNs = result.getRecommendations().keySet();

            if(isHave(result.getRecommendations(),actualFQN))
                tp++;
            else
                fp++;
        }
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.precision = calculatePrecision(tp,fp);
        this.recall = calculateRecall(tp,results.size());
        this.fscore = calculateFscore(this.precision,this.recall);

    }
    private double calculatePrecision(long tp, long fp){
        if(tp+fp == 0)
            return 0;
        else
            return ((tp+0.000001)/(tp+fp+0.000001));
    }
    private double calculateRecall(long tp, long totaltestCases){
        if(totaltestCases == 0)
            return 0;
        else {
            double recall = ((tp + 0.00001) / (totaltestCases + 0.00001));
            return recall > 1 ? 1 - (1 - recall) : recall;
        }
    }
    private double calculateFscore(double precision, double recall){
        if(precision+recall == 0)
            return 0;
        else
            return (2*precision*recall)/(precision+recall);
    }

    private static boolean contains(Set<String> resutls, String eachCase) {
        for(String eachResult:resutls)
            if (eachResult.contains(eachCase) || eachCase.contains(eachResult))
                return true;
        return false;
    }
    private static boolean isHave(List<OLDEntry> resutls, String eachCase) {
        for(OLDEntry eachResult:resutls)
            if (eachResult.getFqn().contains(eachCase) || eachCase.contains(eachResult.getFqn()))
                return true;
        return false;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getRecall() {
        return recall;
    }

    public void setRecall(double recall) {
        this.recall = recall;
    }

    public double getFscore() {
        return fscore;
    }

    public void setFscore(double fscore) {
        this.fscore = fscore;
    }
}

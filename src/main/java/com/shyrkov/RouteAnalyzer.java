package com.shyrkov;

import com.shyrkov.model.Pipeline;
import com.shyrkov.model.ResultModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteAnalyzer {

    public ResultModel findRoute(int startPoint, int pointB, List<Pipeline> pipelinesList, String resPath)
            throws IOException {
        int length = 0;
        List<Integer> lengthResList = new ArrayList<>();
        for (int index = 0; index < pipelinesList.size(); index++) {
            if (startPoint == pipelinesList.get(index).getStartPointId()) {
                checkRoad(index, pipelinesList, startPoint, pointB, length, lengthResList);
            }
        }
        if (lengthResList.isEmpty()) {
            ResultToCsvWriter.writeResult(resPath, false, 0);
            return new ResultModel(false, "");
        } else {
            Collections.sort(lengthResList);
            Integer minLength = lengthResList.get(0);
            ResultToCsvWriter.writeResult(resPath, true, minLength);
            return new ResultModel(true, minLength.toString());
        }
    }

    private static void checkRoad(int index, List<Pipeline> pipelinesList, int pointA, int pointB, int length,
                                  List<Integer> lengthResList) throws IOException {
        for (int jindex = index; jindex < pipelinesList.size(); jindex++)
            if (pointA == pipelinesList.get(jindex).getStartPointId()) {
                if (pointB == pipelinesList.get(jindex).getEndPointId()) {
                    length += pipelinesList.get(jindex).getLength();
                    lengthResList.add(length);
                    break;
                } else if (pointB > pipelinesList.get(jindex).getEndPointId()) {
                    int tempPoint = pointA;
                    pointA = pipelinesList.get(jindex).getEndPointId();
                    length += pipelinesList.get(jindex).getLength();
                    checkRoad(jindex, pipelinesList, pointA, pointB, length, lengthResList);
                    pointA = tempPoint;
                    length -= pipelinesList.get(jindex).getLength();
                } else
                    break;
            }
    }

}

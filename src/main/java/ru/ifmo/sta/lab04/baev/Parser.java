//package ru.ifmo.sta.lab04.baev;
//
///**
//* @author Dmitry Baev charlie@yandex-team.ru
//*         Date: 02.06.13
//*/
//
//import java.io.InputStream;
//
//public class Parser {
//    public static Node parse(InputStream is) throws ParseException {
//        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(is);
//
//        lexicalAnalyzer.nextToken();
//        return A(lexicalAnalyzer);
//    }
//
//    private static Node A(LexicalAnalyzer lexicalAnalyzer) throws ParseException {
//        Token curToken = lexicalAnalyzer.getCurrentToken();
//
//        switch (curToken) {
//            case END:
//                throw new ParseException("Expected A but has none.", lexicalAnalyzer.getPosition());
//            case OPERAND:
//                return new Node(lexicalAnalyzer.getOperand());
//
//            default:
//                if (!lexicalAnalyzer.hasNextToken()) {
//                    throw new ParseException("Expected first OPERAND but has none.", lexicalAnalyzer.getPosition());
//                }
//                lexicalAnalyzer.nextToken();
//                Node first = A(lexicalAnalyzer);
//
//                if (!lexicalAnalyzer.hasNextToken()) {
//                    throw new ParseException("Expected second OPERAND but has none.", lexicalAnalyzer.getPosition());
//                }
//                lexicalAnalyzer.nextToken();
//                Node second = A(lexicalAnalyzer);
//
//                return new Node(curToken, first, second);
//        }
//    }
//}
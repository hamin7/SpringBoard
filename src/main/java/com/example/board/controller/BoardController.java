package com.example.board.controller;

import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<String> writeBoard(@ModelAttribute Board board,
                                            @RequestParam(name="file", required = false) MultipartFile file) {
        ResponseEntity<String> res = null;
        try {
            boardService.writeBoard(board, file);
            res = new ResponseEntity<String>("글 등록 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("글 등록 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/detail")
    public ResponseEntity<Board> detailBoard(
            @RequestParam("num") Integer num) {
        ResponseEntity<Board> res = null;
        try {
            Board board = boardService.detailBoard(num);
            res = new ResponseEntity<Board>(board, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Board>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
}

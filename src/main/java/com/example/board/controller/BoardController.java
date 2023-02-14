package com.example.board.controller;

import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<String> writeBoard(@ModelAttribute Board board,
                                             @RequestParam(name = "file", required = false) MultipartFile file) {
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
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Board>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/image/{filename}")
    public void imageView(@PathVariable String filename,
                          HttpServletResponse response) {
        String path = "/Users/leehamin/upload/";
        try {
            FileInputStream fis = new FileInputStream(path + filename);
            OutputStream out = response.getOutputStream();
            FileCopyUtils.copy(fis, out);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/delete/{num}")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable Integer num) {
        ResponseEntity<Boolean> res = null;
        try {
            boardService.deleteBoard(num);
            res = new ResponseEntity<Boolean>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/boardList")
    public ResponseEntity<List<Board>> boardList() {
        ResponseEntity<List<Board>> res = null;
        try {
            List<Board> totalBoard = boardService.boardList();
            res = new ResponseEntity<List<Board>>(totalBoard, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Board>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @PutMapping("/modify/{num}")
    public ResponseEntity<String> modify(@PathVariable Integer num,
                                         @RequestParam(value = "subject", required = false) String subject,
                                         @RequestParam(value = "content", required = false) String content) {
        ResponseEntity<String> res = null;
        try {
            boardService.modify(num, subject, content);
            res = new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Board>> search(@RequestParam("type") String type,
                                              @RequestParam("word") String word) {
        ResponseEntity<List<Board>> res = null;
        try {
            List<Board> searchList = boardService.search(type, word);
            res = new ResponseEntity<List<Board>>(searchList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Board>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }
}

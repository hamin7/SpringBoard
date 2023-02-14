package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    public void writeBoard(Board board, MultipartFile file) throws Exception {
        if (file!=null && !file.isEmpty()) {
            String path = "/Users/leehamin/upload/";
            File destfile = new File(path+file.getOriginalFilename());
            file.transferTo(destfile);
            board.setFilename(file.getOriginalFilename());
        }
        boardRepository.save(board);
    }
}

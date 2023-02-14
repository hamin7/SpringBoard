package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public void writeBoard(Board board, MultipartFile file) throws Exception {
        if (file != null && !file.isEmpty()) {
            String path = "/Users/leehamin/upload/";
            File destfile = new File(path + file.getOriginalFilename());
            file.transferTo(destfile);
            board.setFilename(file.getOriginalFilename());
        }
        boardRepository.save(board);
    }

    public Board detailBoard(Integer num) throws Exception {
        int updateCnt = boardRepository.readCntInc(num);
        if (updateCnt == 0) throw new Exception("글번호 오류");
        Optional<Board> oboard = boardRepository.findById(num);
        if (oboard.isEmpty()) throw new Exception("글번호 오류");
        return oboard.get();
    }

    public void deleteBoard(Integer num) throws Exception {
        boardRepository.deleteById(num);
    }

    public List<Board> boardList() throws Exception {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "num"));
    }

    public void modify(Integer num, String subject, String content) throws Exception {
        Optional<Board> oboard = boardRepository.findById(num);
        if (oboard.isEmpty()) throw new Exception("글 번호 오류");
        Board board = oboard.get();
        if (subject != null) board.setSubject(subject);
        if (content != null) board.setContent(content);
        boardRepository.save(board);
    }

    public List<Board> search(String type, String word) throws Exception {
        if (word == null || word == "") {
            return boardList();
        } else if (type.equals("writer")) {
            return boardRepository.findByWriterContainsOrderByNumDesc(word);
        } else if (type.equals("subject")) {
            return boardRepository.findBySubjectContainsOrderByNumDesc(word);
        } else {
            return boardRepository.findByContentContainsOrderByNumDesc(word);
        }
    }
}

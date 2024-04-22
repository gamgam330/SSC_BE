package com.sithub.sithub.controller;

import com.sithub.sithub.Service.MariaService;
import com.sithub.sithub.Service.SnapshotService;
import com.sithub.sithub.domain.Snapshot;
import com.sithub.sithub.requestDTO.CodeDTO;
import com.sithub.sithub.requestDTO.MariaDTO;
import com.sithub.sithub.requestDTO.S3UploadDTO;
import com.sithub.sithub.requestDTO.SnapshotRequestDTO;
import com.sithub.sithub.responseDTO.SnapshotDTO;
import com.sithub.sithub.responseDTO.SnapshotListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SnapshotController {

    private final SnapshotService snapshotService;
    private final MariaService mariaService;

//    @PostMapping("/snapshot")
//    public void saveSnapshot(@RequestBody SnapshotRequestDTO request/*MariaDTO mariaDTO*/) {
//        snapshotService.saveSnapshot(request.getRoomId(), request.getFileName(), request.getCode());
//        //mariaService.saveSnapshot(mariaDTO.getRoomId(), mariaDTO.getCode());
//    }


    //방 들어갔을때 받아가는 데이터
    @GetMapping("/snapshots/{roomId}")
    public SnapshotDTO getSnapshotsByRoomId(@PathVariable("roomId") String roomId) {
        return snapshotService.getSnapshotsByRoomId(roomId);
    }

    //해당 방에서 코드를 수정했을때 저장
    @PatchMapping("/code/{roomId}")
    public void updateCode(@PathVariable("roomId") String roomId, @RequestBody CodeDTO code) {
        snapshotService.updateCodes(roomId, code.getCode(), code.getLineNumber());
        //mariaService.updateCodes(roomId, code.getCode());
    }

    //새 프로젝트 파일 받기
    @PostMapping("/{teamName}/snapshots/save")
    public void save(
            @RequestPart(value = "files") List<MultipartFile> files,
            @PathVariable("teamName") String teamName) throws IOException {
        snapshotService.saveFile(files, teamName);
    }

    //작업중인 프로젝트 S3에 업로드
    @PostMapping("/s3/upload")
    public void upload(@RequestBody S3UploadDTO dto) throws IOException {
        snapshotService.uploadToS3(dto.getTeamName(), dto.getComment());
    }

    // 작업중인 프로젝트 파일 목록
    @GetMapping("/snapshot/list/{teamName}")
    public List<SnapshotListDTO> snapshotList(@PathVariable("teamName") String teamName) {
        return snapshotService.getSnapshotList(teamName);
    }

    // 선택한 파일 코드 반환
    @GetMapping("/snapshot/{snapshotId}")
    public String snapshot(@PathVariable("snapshotId") String snapshotId) {
        return snapshotService.getSnapshot(snapshotId);
    }

    @GetMapping("/test/file")
    public String test() {
        return snapshotService.test();
    }
}
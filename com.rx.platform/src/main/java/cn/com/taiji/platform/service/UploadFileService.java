package cn.com.taiji.platform.service;

import java.util.List;

import cn.com.taiji.platform.entity.UploadFile;

public interface UploadFileService {

	void saveUploadFile(UploadFile uf);

	void delFile(String[] ids);

	List fileList(String fkid);

	UploadFile getUploadFileByFid(String fid);

}

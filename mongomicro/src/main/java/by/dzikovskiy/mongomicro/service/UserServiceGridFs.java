package by.dzikovskiy.mongomicro.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceGridFs {
    private final GridFsTemplate gridFsTemplate;
    private static final String USER_ID_METADATA_CRITERIA = "metadata.userId";

    public void save(final Long userId, MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("userId", userId);

        gridFsTemplate.delete(new Query().addCriteria(Criteria.where(USER_ID_METADATA_CRITERIA).is(userId)));
        gridFsTemplate.store(file.getInputStream(), file.getName(), "image/jpeg", metaData);
    }

    public Optional<Binary> getPhoto(final Long userId) throws IOException {
        GridFSFile gridFsFile = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where(USER_ID_METADATA_CRITERIA).is(userId)));

        if (gridFsFile == null) {
            log.debug("File not found with id: " + userId);
            return Optional.empty();
        }
        if (!gridFsTemplate.getResource(gridFsFile).exists()) {
            return Optional.empty();
        }

        return Optional.of(new Binary(gridFsTemplate.getResource(gridFsFile).getInputStream().readAllBytes()));
    }

    public void delete(final Long userId) {
        gridFsTemplate.delete(new Query().addCriteria(Criteria.where(USER_ID_METADATA_CRITERIA).is(userId)));
    }

}

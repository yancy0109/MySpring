package java.com.yancy.springframework.beans.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源加载接口
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
}

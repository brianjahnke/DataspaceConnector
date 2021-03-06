package de.fraunhofer.isst.dataspaceconnector.filter.httptracing.internal;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import lombok.extern.log4j.Log4j2;
import org.springframework.util.StreamUtils;

/**
 * Wraps incoming HTTP requests too read the message payload multiple times.
 */
@Log4j2
public final class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * The original request body.
     */
    private transient byte[] requestBody;

    /**
     * Whether the original request body has been copied.
     */
    private transient boolean isBufferFilled;

    /**
     * Default constructor.
     * @param request The request to be wrapped
     */
    public RequestWrapper(final HttpServletRequest request) {
        super(request);
    }

    /**
     * Get the request body of the message.
     * @return The request body
     * @throws IOException if the request body could not be read
     */
    public byte[] getRequestBody() throws IOException {
        final var output = isBufferFilled ? requestBody : cloneRequestBody();
        return Arrays.copyOf(output, output.length);
    }

    private byte[] cloneRequestBody() throws IOException {
        final var inputStream = super.getInputStream();
        if (inputStream != null) {
            requestBody = StreamUtils.copyToByteArray(inputStream);
            isBufferFilled = true;
        }

        return requestBody;
    }

    /**
     * Get the request body of the message as stream.
     * @return The request body as stream
     * @throws IOException if the request body could not be read
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomServletInputStream(getRequestBody());
    }

    @Override
    public BufferedReader getReader() {
        final var bais = new ByteArrayInputStream(this.requestBody);
        return new BufferedReader(new InputStreamReader(bais));
    }

    /**
     * Custom input stream returning a clone of the original request.
     */
    private static class CustomServletInputStream extends ServletInputStream {
        /**
         * Copy of the input stream.
         */
        private final transient ByteArrayInputStream buffer;

        /**
         * Default constructor.
         * @param contents The request body.
         */
        /* default */ CustomServletInputStream(final byte[] contents) {
            super();
            buffer = new ByteArrayInputStream(contents);
        }

        @Override
        public int read() {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(final ReadListener listener) {
            if (log.isErrorEnabled()) {
                log.error("Tried to set read listener.");
            }
        }
    }
}

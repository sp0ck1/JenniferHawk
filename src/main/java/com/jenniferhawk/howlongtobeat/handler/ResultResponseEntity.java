package com.jenniferhawk.howlongtobeat.handler;
import java.io.Serializable;
import org.springframework.hateoas.RepresentationModel;

/**
     * The generic json result
     *
     * @author Christian Katzorke ckatzorke@gmail.com
     *
     */
    public class ResultResponseEntity<T> extends RepresentationModel implements Serializable {
        private static final long serialVersionUID = 2569196901438096359L;

        private final T result;

        public ResultResponseEntity(T result) {
            this.result = result;
        }

        /**
         * @return the result
         */
        public T getResult() {
            return result;
        }

    }



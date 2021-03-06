/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.module.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.val;
import org.springframework.data.domain.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @author 应卓
 */
@JsonDeserialize(as = PageMixIn.SimplePageImpl.class)
public interface PageMixIn {

    public static class SimplePageImpl<T> implements Page<T> {
        private final Page<T> delegate;
        private final int pageNumber;
        private final int pageSize;

        public SimplePageImpl(
                @JsonProperty("content") List<T> content,
                @JsonProperty("page") int number,
                @JsonProperty("size") int size,
                @JsonProperty("totalElements") long totalElements) {

            this.pageNumber = number;
            this.pageSize = size;
            this.delegate = new PageImpl<>(content, PageRequest.of(number, size), totalElements);
        }

        @Override
        @JsonProperty
        public int getTotalPages() {
            return delegate.getTotalPages();
        }

        @Override
        @JsonProperty
        public long getTotalElements() {
            return delegate.getTotalElements();
        }

        @Override
        @JsonProperty("page")
        public int getNumber() {
            return delegate.getNumber();
        }

        @Override
        @JsonProperty
        public int getSize() {
            return delegate.getSize();
        }

        @Override
        @JsonProperty
        public int getNumberOfElements() {
            return delegate.getNumberOfElements();
        }

        @Override
        @JsonProperty
        public List<T> getContent() {
            return delegate.getContent();
        }

        @Override
        @JsonProperty
        public boolean hasContent() {
            return delegate.hasContent();
        }

        @Override
        @JsonIgnore
        public Sort getSort() {
            return delegate.getSort();
        }

        @Override
        @JsonProperty
        public boolean isFirst() {
            return delegate.isFirst();
        }

        @Override
        @JsonProperty
        public boolean isLast() {
            return delegate.isLast();
        }

        @Override
        @JsonIgnore
        public boolean hasNext() {
            return delegate.hasNext();
        }

        @Override
        @JsonIgnore
        public boolean hasPrevious() {
            return delegate.hasPrevious();
        }

        @Override
        @JsonIgnore
        public Pageable nextPageable() {
            return delegate.nextPageable();
        }

        @Override
        @JsonIgnore
        public Pageable previousPageable() {
            return delegate.previousPageable();
        }

        @Override
        @JsonIgnore
        public <U> Page<U> map(Function<? super T, ? extends U> converter) {
            return delegate.map(converter);
        }

        @Override
        @JsonIgnore
        public Iterator<T> iterator() {
            return delegate.iterator();
        }

        @Override
        public Pageable getPageable() {
            val pageable = delegate.getPageable();
            if ("org.springframework.data.domain.Unpaged".equals(pageable.getClass().getName())) {
                return PageRequest.of(pageNumber, pageSize);
            }
            return pageable;
        }
    }

}

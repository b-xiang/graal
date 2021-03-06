/*
 * Copyright (c) 2013, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The Universal Permissive License (UPL), Version 1.0
 *
 * Subject to the condition set forth below, permission is hereby granted to any
 * person obtaining a copy of this software, associated documentation and/or
 * data (collectively the "Software"), free of charge and under any and all
 * copyright rights in the Software, and any and all patent rights owned or
 * freely licensable by each licensor hereunder covering either (i) the
 * unmodified Software as contributed to or provided by such licensor, or (ii)
 * the Larger Works (as defined below), to deal in both
 *
 * (a) the Software, and
 *
 * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 * one is included with the Software each a "Larger Work" to which the Software
 * is contributed by such licensors),
 *
 * without restriction, including without limitation the rights to copy, create
 * derivative works of, display, perform, and distribute the Software and make,
 * use, sell, offer for sale, import, export, have made, and have sold the
 * Software and the Larger Work(s), and to sublicense the foregoing rights on
 * either these or other terms.
 *
 * This license is subject to the following condition:
 *
 * The above copyright notice and either this complete permission notice or at a
 * minimum a reference to the UPL must be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.oracle.truffle.api.source;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.source.Source.SourceBuilder;
import java.nio.file.Path;

final class SourceAccessor extends Accessor {

    static final SourceAccessor ACCESSOR = new SourceAccessor();

    protected SourceAccessor() {
    }

    @Override
    protected SourceSupport sourceSupport() {
        return new SourceSupportImpl();
    }

    @Override
    protected LanguageSupport languageSupport() {
        return super.languageSupport();
    }

    @Override
    protected EngineSupport engineSupport() {
        return super.engineSupport();
    }

    static Collection<ClassLoader> allLoaders() {
        return ACCESSOR.loaders();
    }

    static Path getPath(TruffleFile file) {
        return ACCESSOR.languageSupport().getPath(file);
    }

    static final class SourceSupportImpl extends Accessor.SourceSupport {

        @Override
        public Source copySource(Source source) {
            return source.copy();
        }

        @Override
        public Object getSourceIdentifier(Source source) {
            return source.getSourceId();
        }

        @Override
        public org.graalvm.polyglot.Source getPolyglotSource(Source source) {
            return source.polyglotSource;
        }

        @Override
        public void setPolyglotSource(Source source, org.graalvm.polyglot.Source polyglotSource) {
            source.polyglotSource = polyglotSource;
        }

        @Override
        public String findMimeType(File file) throws IOException {
            return Source.findMimeType(file.toPath(), null);
        }

        @Override
        public String findMimeType(URL url) throws IOException {
            return Source.findMimeType(url);
        }

        @Override
        public SourceBuilder newBuilder(String language, File origin) {
            return Source.newBuilder(language, origin);
        }

        @Override
        public boolean isLegacySource(Source source) {
            return source.isLegacy();
        }

    }
}

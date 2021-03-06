// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: netty/SubscribeResponse.proto

package netty.googleProtobuf;

public final class SubscribeResponseProto {
    private SubscribeResponseProto() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface SubscribeResponseOrBuilder extends
            // @@protoc_insertion_point(interface_extends:netty.SubscribeResponse)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>int32 requestId = 1;</code>
         */
        int getRequestId();

        /**
         * <code>string respCode = 2;</code>
         */
        String getRespCode();

        /**
         * <code>string respCode = 2;</code>
         */
        com.google.protobuf.ByteString
        getRespCodeBytes();

        /**
         * <code>string decribe = 3;</code>
         */
        String getDecribe();

        /**
         * <code>string decribe = 3;</code>
         */
        com.google.protobuf.ByteString
        getDecribeBytes();
    }

    /**
     * Protobuf type {@code netty.SubscribeResponse}
     */
    public static final class SubscribeResponse extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:netty.SubscribeResponse)
            SubscribeResponseOrBuilder {
        private static final long serialVersionUID = 0L;

        // Use SubscribeResponse.newBuilder() to construct.
        private SubscribeResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SubscribeResponse() {
            requestId_ = 0;
            respCode_ = "";
            decribe_ = "";
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        private SubscribeResponse(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownFieldProto3(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 8: {

                            requestId_ = input.readInt32();
                            break;
                        }
                        case 18: {
                            String s = input.readStringRequireUtf8();

                            respCode_ = s;
                            break;
                        }
                        case 26: {
                            String s = input.readStringRequireUtf8();

                            decribe_ = s;
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return SubscribeResponseProto.internal_static_netty_SubscribeResponse_descriptor;
        }

        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return SubscribeResponseProto.internal_static_netty_SubscribeResponse_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            SubscribeResponse.class, Builder.class);
        }

        public static final int REQUESTID_FIELD_NUMBER = 1;
        private int requestId_;

        /**
         * <code>int32 requestId = 1;</code>
         */
        public int getRequestId() {
            return requestId_;
        }

        public static final int RESPCODE_FIELD_NUMBER = 2;
        private volatile Object respCode_;

        /**
         * <code>string respCode = 2;</code>
         */
        public String getRespCode() {
            Object ref = respCode_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                respCode_ = s;
                return s;
            }
        }

        /**
         * <code>string respCode = 2;</code>
         */
        public com.google.protobuf.ByteString
        getRespCodeBytes() {
            Object ref = respCode_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                respCode_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        public static final int DECRIBE_FIELD_NUMBER = 3;
        private volatile Object decribe_;

        /**
         * <code>string decribe = 3;</code>
         */
        public String getDecribe() {
            Object ref = decribe_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                decribe_ = s;
                return s;
            }
        }

        /**
         * <code>string decribe = 3;</code>
         */
        public com.google.protobuf.ByteString
        getDecribeBytes() {
            Object ref = decribe_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                decribe_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        private byte memoizedIsInitialized = -1;

        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            if (requestId_ != 0) {
                output.writeInt32(1, requestId_);
            }
            if (!getRespCodeBytes().isEmpty()) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 2, respCode_);
            }
            if (!getDecribeBytes().isEmpty()) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 3, decribe_);
            }
            unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            if (requestId_ != 0) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(1, requestId_);
            }
            if (!getRespCodeBytes().isEmpty()) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, respCode_);
            }
            if (!getDecribeBytes().isEmpty()) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, decribe_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SubscribeResponse)) {
                return super.equals(obj);
            }
            SubscribeResponse other = (SubscribeResponse) obj;

            boolean result = true;
            result = result && (getRequestId()
                    == other.getRequestId());
            result = result && getRespCode()
                    .equals(other.getRespCode());
            result = result && getDecribe()
                    .equals(other.getDecribe());
            result = result && unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            hash = (37 * hash) + REQUESTID_FIELD_NUMBER;
            hash = (53 * hash) + getRequestId();
            hash = (37 * hash) + RESPCODE_FIELD_NUMBER;
            hash = (53 * hash) + getRespCode().hashCode();
            hash = (37 * hash) + DECRIBE_FIELD_NUMBER;
            hash = (53 * hash) + getDecribe().hashCode();
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static SubscribeResponse parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SubscribeResponse parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SubscribeResponse parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SubscribeResponse parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SubscribeResponse parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SubscribeResponse parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SubscribeResponse parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static SubscribeResponse parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SubscribeResponse parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static SubscribeResponse parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SubscribeResponse parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static SubscribeResponse parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SubscribeResponse prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(
                BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code netty.SubscribeResponse}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:netty.SubscribeResponse)
                SubscribeResponseOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return SubscribeResponseProto.internal_static_netty_SubscribeResponse_descriptor;
            }

            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return SubscribeResponseProto.internal_static_netty_SubscribeResponse_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                SubscribeResponse.class, Builder.class);
            }

            // Construct using netty.googleProtobuf.SubscribeResponseProto.SubscribeResponse.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                }
            }

            public Builder clear() {
                super.clear();
                requestId_ = 0;

                respCode_ = "";

                decribe_ = "";

                return this;
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return SubscribeResponseProto.internal_static_netty_SubscribeResponse_descriptor;
            }

            public SubscribeResponse getDefaultInstanceForType() {
                return SubscribeResponse.getDefaultInstance();
            }

            public SubscribeResponse build() {
                SubscribeResponse result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public SubscribeResponse buildPartial() {
                SubscribeResponse result = new SubscribeResponse(this);
                result.requestId_ = requestId_;
                result.respCode_ = respCode_;
                result.decribe_ = decribe_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }

            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return (Builder) super.setField(field, value);
            }

            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof SubscribeResponse) {
                    return mergeFrom((SubscribeResponse) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(SubscribeResponse other) {
                if (other == SubscribeResponse.getDefaultInstance()) return this;
                if (other.getRequestId() != 0) {
                    setRequestId(other.getRequestId());
                }
                if (!other.getRespCode().isEmpty()) {
                    respCode_ = other.respCode_;
                    onChanged();
                }
                if (!other.getDecribe().isEmpty()) {
                    decribe_ = other.decribe_;
                    onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                SubscribeResponse parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (SubscribeResponse) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int requestId_;

            /**
             * <code>int32 requestId = 1;</code>
             */
            public int getRequestId() {
                return requestId_;
            }

            /**
             * <code>int32 requestId = 1;</code>
             */
            public Builder setRequestId(int value) {

                requestId_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>int32 requestId = 1;</code>
             */
            public Builder clearRequestId() {

                requestId_ = 0;
                onChanged();
                return this;
            }

            private Object respCode_ = "";

            /**
             * <code>string respCode = 2;</code>
             */
            public String getRespCode() {
                Object ref = respCode_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    respCode_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>string respCode = 2;</code>
             */
            public com.google.protobuf.ByteString
            getRespCodeBytes() {
                Object ref = respCode_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    respCode_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>string respCode = 2;</code>
             */
            public Builder setRespCode(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                respCode_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>string respCode = 2;</code>
             */
            public Builder clearRespCode() {

                respCode_ = getDefaultInstance().getRespCode();
                onChanged();
                return this;
            }

            /**
             * <code>string respCode = 2;</code>
             */
            public Builder setRespCodeBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                checkByteStringIsUtf8(value);

                respCode_ = value;
                onChanged();
                return this;
            }

            private Object decribe_ = "";

            /**
             * <code>string decribe = 3;</code>
             */
            public String getDecribe() {
                Object ref = decribe_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    decribe_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>string decribe = 3;</code>
             */
            public com.google.protobuf.ByteString
            getDecribeBytes() {
                Object ref = decribe_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    decribe_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>string decribe = 3;</code>
             */
            public Builder setDecribe(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                decribe_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>string decribe = 3;</code>
             */
            public Builder clearDecribe() {

                decribe_ = getDefaultInstance().getDecribe();
                onChanged();
                return this;
            }

            /**
             * <code>string decribe = 3;</code>
             */
            public Builder setDecribeBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                checkByteStringIsUtf8(value);

                decribe_ = value;
                onChanged();
                return this;
            }

            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFieldsProto3(unknownFields);
            }

            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:netty.SubscribeResponse)
        }

        // @@protoc_insertion_point(class_scope:netty.SubscribeResponse)
        private static final SubscribeResponse DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new SubscribeResponse();
        }

        public static SubscribeResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        private static final com.google.protobuf.Parser<SubscribeResponse>
                PARSER = new com.google.protobuf.AbstractParser<SubscribeResponse>() {
            public SubscribeResponse parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new SubscribeResponse(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<SubscribeResponse> parser() {
            return PARSER;
        }

        @Override
        public com.google.protobuf.Parser<SubscribeResponse> getParserForType() {
            return PARSER;
        }

        public SubscribeResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_netty_SubscribeResponse_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_netty_SubscribeResponse_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\n\035netty/SubscribeResponse.proto\022\005netty\"I" +
                        "\n\021SubscribeResponse\022\021\n\trequestId\030\001 \001(\005\022\020" +
                        "\n\010respCode\030\002 \001(\t\022\017\n\007decribe\030\003 \001(\tB.\n\024net" +
                        "ty.googleProtobufB\026SubscribeResponseProt" +
                        "ob\006proto3"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
                    public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                        }, assigner);
        internal_static_netty_SubscribeResponse_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_netty_SubscribeResponse_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_netty_SubscribeResponse_descriptor,
                new String[]{"RequestId", "RespCode", "Decribe",});
    }

    // @@protoc_insertion_point(outer_class_scope)
}

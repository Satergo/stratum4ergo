package com.satergo.stratum4ergo;

import com.satergo.stratum4ergo.data.ShareData;

public sealed interface JobManagerEvent {
	record NewBlock(BlockTemplate blockTemplate) implements JobManagerEvent {}
	record UpdatedBlock(BlockTemplate blockTemplate) implements JobManagerEvent {}
	record Share(ShareData shareData, byte[] nonce) implements JobManagerEvent {}
}

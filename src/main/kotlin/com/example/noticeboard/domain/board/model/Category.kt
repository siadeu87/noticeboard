package com.example.noticeboard.domain.board.model

import com.fasterxml.jackson.annotation.JsonCreator
import org.apache.commons.lang3.EnumUtils

enum class Category(val categoryInKorean: String) {
    ALL("전체"),
    NOTICE("공지"),
    INFORMATION("정보"),
    COMMON("일반");

    companion object {
        @JvmStatic
        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        fun parse(name: String?): Category =
            name?.let { EnumUtils.getEnumIgnoreCase(Category::class.java, it.trim()) }
                ?: throw IllegalArgumentException("해당하는 카테고리가 없습니다.") // TODO 예외 메시지
    }
}
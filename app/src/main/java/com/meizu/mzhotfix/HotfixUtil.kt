package com.meizu.mzhotfix

import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import java.io.File


/**
 * author : Samuel
 * e-mail : xingtai.wei@xjmz.com
 * time   : 2025/3/5 上午10:16
 * desc   :
 */
object HotFixUtil {
    fun inject(loader: ClassLoader, patch: File) {
        try {
            val pathList = getPathList(loader)
            val oldDexElements = getDexElements(pathList)

            DexClassLoader(
                patch.absolutePath,
                patch.parent,
                null,
                loader
            ).apply {
                val patchPathList = getPathList(this)
                val patchDexElements = getDexElements(patchPathList)
                val newDexElements = combineArray(patchDexElements, oldDexElements)
                setDexElements(pathList, newDexElements)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getPathList(loader: ClassLoader): Any {
        val pathListField = BaseDexClassLoader::class.java.getDeclaredField("pathList")
//        val pathListField = loader::class.java.getDeclaredField("pathList")
        pathListField.isAccessible = true

        return pathListField.get(loader)!!
    }

    private fun getDexElements(pathList: Any): Any {
        val dexElementsField = pathList::class.java.getDeclaredField("dexElements")
        dexElementsField.isAccessible = true
        return dexElementsField.get(pathList)!!
    }

    private fun setDexElements(pathList: Any, elements: Any) {
        val dexElementsField = pathList::class.java.getDeclaredField("dexElements")
        dexElementsField.isAccessible = true
        dexElementsField.set(pathList, elements)
    }

    private fun combineArray(first: Any, second: Any): Any {
        val firstLength = java.lang.reflect.Array.getLength(first)
        val secondLength = java.lang.reflect.Array.getLength(second)
        val newArray = java.lang.reflect.Array.newInstance(
            first.javaClass.componentType!!,
            firstLength + secondLength
        )

        System.arraycopy(first, 0, newArray, 0, firstLength)
        System.arraycopy(second, 0, newArray, firstLength, secondLength)
        return newArray
    }
}
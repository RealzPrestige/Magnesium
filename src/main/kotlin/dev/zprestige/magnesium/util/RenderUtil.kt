package dev.zprestige.magnesium.util

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.render.*
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.Matrix4f
import java.awt.Color

class RenderUtil {

    companion object {
        fun fill(matrices: MatrixStack, x: Float, y: Float, width: Float, height: Float, color: Color) {
            DrawableHelper.fill(matrices, x.toInt(), y.toInt(), width.toInt(), height.toInt(), color.rgb)
        }

        fun outline(matrices: MatrixStack, x: Float, y: Float, width: Float, height: Float, size: Float, color: Color) {
            val x1 = x.toInt()
            val y1 = y.toInt()
            val width1 = width.toInt()
            val height1 = height.toInt()
            val size1 = size.toInt()
            DrawableHelper.fill(matrices, x1, y1, width1 + size1, y1 - size1, color.rgb)
            DrawableHelper.fill(matrices, x1, y1 - size1, x1 - size1, height1 + size1, color.rgb)
            DrawableHelper.fill(matrices, x1, height1 + size1, width1 + size1, height1, color.rgb)
            DrawableHelper.fill(matrices, width1 + size1, y1, width1, height1, color.rgb)
        }

        fun fillGradient(matrices: MatrixStack, startX: Float, startY: Float, endX: Float, endY: Float, colorStart: Color, colorEnd: Color, horizontal: Boolean) {
            fillGradient(matrices, startX, startY, endX, endY, colorStart.rgb, colorEnd.rgb, horizontal)
        }

        private fun fillGradient(matrices: MatrixStack, startX: Float, startY: Float, endX: Float, endY: Float, colorStart: Int, colorEnd: Int, horizontal: Boolean) {
            RenderSystem.disableTexture()
            RenderSystem.enableBlend()
            RenderSystem.defaultBlendFunc()
            RenderSystem.setShader { GameRenderer.getPositionColorShader() }
            val tessellator = Tessellator.getInstance()
            val bufferBuilder = tessellator.buffer
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR)
            fillGradient(matrices.peek().positionMatrix,
                bufferBuilder,
                startX,
                startY,
                endX,
                endY,
                colorStart,
                colorEnd,
                horizontal
            )
            tessellator.draw()
            RenderSystem.disableBlend()
            RenderSystem.enableTexture()
        }

        private fun fillGradient(matrix: Matrix4f, builder: BufferBuilder, startX: Float, startY: Float, endX: Float, endY: Float, colorStart: Int, colorEnd: Int, horizontal: Boolean) {
            val f = (colorStart shr 24 and 255).toFloat() / 255.0f
            val g = (colorStart shr 16 and 255).toFloat() / 255.0f
            val h = (colorStart shr 8 and 255).toFloat() / 255.0f
            val i = (colorStart and 255).toFloat() / 255.0f
            val j = (colorEnd shr 24 and 255).toFloat() / 255.0f
            val k = (colorEnd shr 16 and 255).toFloat() / 255.0f
            val l = (colorEnd shr 8 and 255).toFloat() / 255.0f
            val m = (colorEnd and 255).toFloat() / 255.0f
            if (horizontal) {
                builder.vertex(matrix, endX, startY, 0.0f).color(k, l, m, j).next()
                builder.vertex(matrix, startX, startY, 0.0f).color(g, h, i, f).next()
                builder.vertex(matrix, startX, endY, 0.0f).color(g, h, i, f).next()
                builder.vertex(matrix, endX, endY, 0.0f).color(k, l, m, j).next()
            } else {
                builder.vertex(matrix, endX, startY, 0.0f).color(g, h, i, f).next()
                builder.vertex(matrix, startX, startY, 0.0f).color(g, h, i, f).next()
                builder.vertex(matrix, startX, endY, 0.0f).color(k, l, m, j).next()
                builder.vertex(matrix, endX, endY, 0.0f).color(k, l, m, j).next()
            }
        }

        fun fillGradient(matrices: MatrixStack, startX: Float, startY: Float, endX: Float, endY: Float, colorOne: Color, colorTwo: Color, colorThree: Color, colorFour: Color) {
            fillGradient(matrices,
                startX,
                startY,
                endX,
                endY,
                colorOne.rgb,
                colorTwo.rgb,
                colorThree.rgb,
                colorFour.rgb
            )
        }

        private fun fillGradient(matrices: MatrixStack, startX: Float, startY: Float, endX: Float, endY: Float, colorOne: Int, colorTwo: Int, colorThree: Int, colorFour: Int) {
            RenderSystem.disableTexture()
            RenderSystem.enableBlend()
            RenderSystem.defaultBlendFunc()
            RenderSystem.setShader { GameRenderer.getPositionColorShader() }
            val tessellator = Tessellator.getInstance()
            val bufferBuilder = tessellator.buffer
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR)
            fillGradient(matrices.peek().positionMatrix,
                bufferBuilder,
                startX,
                startY,
                endX,
                endY,
                colorOne,
                colorTwo,
                colorThree,
                colorFour
            )
            tessellator.draw()
            RenderSystem.disableBlend()
            RenderSystem.enableTexture()
        }

        private fun fillGradient(matrix: Matrix4f, builder: BufferBuilder, x: Float, y: Float, width: Float, height: Float, colorOne: Int, colorTwo: Int, colorThree: Int, colorFour: Int) {
            val f = (colorOne shr 24 and 255).toFloat() / 255.0f
            val g = (colorOne shr 16 and 255).toFloat() / 255.0f
            val h = (colorOne shr 8 and 255).toFloat() / 255.0f
            val i = (colorOne and 255).toFloat() / 255.0f

            val j = (colorTwo shr 24 and 255).toFloat() / 255.0f
            val k = (colorTwo shr 16 and 255).toFloat() / 255.0f
            val l = (colorTwo shr 8 and 255).toFloat() / 255.0f
            val m = (colorTwo and 255).toFloat() / 255.0f

            val n = (colorThree shr 24).toFloat() / 255.0f
            val o = (colorThree shr 16).toFloat() / 255.0f
            val p = (colorThree shr 8).toFloat() / 255.0f
            val q = (colorThree and 255).toFloat() / 255.0f

            val r = (colorFour shr 24).toFloat() / 255.0f
            val s = (colorFour shr 16).toFloat() / 255.0f
            val t = (colorFour shr 8).toFloat() / 255.0f
            val u = (colorFour and 255).toFloat() / 255.0f

            builder.vertex(matrix, width, y, 0.0f).color(k, l, m, j).next()
            builder.vertex(matrix, x, y, 0.0f).color(g, h, i, f).next()
            builder.vertex(matrix, x, height, 0.0f).color(o, p, q, n).next()
            builder.vertex(matrix, width, height, 0.0f).color(s, t, u, r).next()
        }

        fun drawTexture(matrices: MatrixStack, identifier: Identifier, x: Int, y: Int, z: Int, u: Float, v: Float, width: Int, height: Int, textureWidth: Int, textureHeight: Int) {
            RenderSystem.setShader { GameRenderer.getPositionTexShader() }
            RenderSystem.setShaderTexture(0, identifier)
            RenderSystem.enableDepthTest()
            drawTexture(matrices,
                x,
                x + width,
                y,
                y + height,
                z,
                width,
                height,
                u,
                v,
                textureWidth,
                textureHeight
            )
        }

        private fun drawTexture(matrices: MatrixStack, x0: Int, x1: Int, y0: Int, y1: Int, z: Int, regionWidth: Int, regionHeight: Int, u: Float, v: Float, textureWidth: Int, textureHeight: Int) {
            drawTexturedQuad(matrices.peek().positionMatrix,
                x0,
                x1,
                y0,
                y1,
                z,
                (u + 0.0f) / textureWidth.toFloat(),
                (u + regionWidth.toFloat()) / textureWidth.toFloat(),
                (v + 0.0f) / textureHeight.toFloat(),
                (v + regionHeight.toFloat()) / textureHeight.toFloat()
            )
        }

        private fun drawTexturedQuad(matrix: Matrix4f, x0: Int, x1: Int, y0: Int, y1: Int, z: Int, u0: Float, u1: Float, v0: Float, v1: Float) {
            RenderSystem.setShader { GameRenderer.getPositionTexShader() }
            val bufferBuilder = Tessellator.getInstance().buffer
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE)
            bufferBuilder.vertex(matrix, x0.toFloat(), y1.toFloat(), z.toFloat()).texture(u0, v1).next()
            bufferBuilder.vertex(matrix, x1.toFloat(), y1.toFloat(), z.toFloat()).texture(u1, v1).next()
            bufferBuilder.vertex(matrix, x1.toFloat(), y0.toFloat(), z.toFloat()).texture(u1, v0).next()
            bufferBuilder.vertex(matrix, x0.toFloat(), y0.toFloat(), z.toFloat()).texture(u0, v0).next()
            bufferBuilder.end()
            BufferRenderer.draw(bufferBuilder)
        }

    }
}
# -*- coding:utf8 -*-
from PIL import Image
import pytesseract
#  下载图片识别的Windows安装包 :https://github.com/UB-Mannheim/tesseract/wiki
#  百度云图片识别：https://cloud.baidu.com/doc/OCR/OCR-Python-SDK.html

# 如果运行程序时提示找不到哦tesseract，则修改pytesseract.py的37行，贴上完整的tesseract.exe路径
# 例如 tesseract_cmd = 'C:/Program Files/Tesseract-OCR/tesseract.exe'


class Languages:
    CHS = 'chi_sim'
    CHT = 'chi_tra'
    ENG = 'eng'


def img_to_str(image_path, lang=Languages.ENG):
    return pytesseract.image_to_string(Image.open(image_path), lang)


if __name__ == '__main__':
    print(img_to_str('image/222.png', lang=Languages.CHS))

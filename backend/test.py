import markdown
from bs4 import BeautifulSoup


def markdown_to_text(markdown_text: str):
    html_content = markdown.markdown(markdown_text)
    soup = BeautifulSoup(html_content, "html.parser")
    plain_text = soup.get_text(separator="\n")
    return plain_text


if __name__ == "__main__":
    markdown_text = "\n# 标题\n\n这是一段 **Markdown** 文本。\n\n- 列表项 1\n- 列表项 2\n\n[链接](https://www.example.com)"
    plain_text = markdown_to_text(markdown_text)
    print(plain_text)

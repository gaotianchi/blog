import re
from typing import cast


def get_all_image_url(text: str) -> list[str]:
    pattern = re.compile(r"!\[.*?\]\((.*?)\)", re.MULTILINE)
    search_results = cast(list[str], re.findall(pattern, text))
    return search_results


TEXT = """
## 😲 md-editor-v3

Markdown Editor for Vue3, developed in jsx and typescript, support different themes、beautify content by prettier.

### 🤖 Base

**bold**, <u>underline</u>, _italic_, ~~line-through~~, superscript<sup>26</sup>, subscript<sub>1</sub>, `inline code`, [link](https://github.com/imzbf)

> quote: I Have a Dream

1. So even though we face the difficulties of today and tomorrow, I still have a dream.
2. It is a dream deeply rooted in the American dream.
3. I have a dream that one day this nation will rise up.

- [ ] Friday
- [ ] Saturday
- [x] Sunday

![Picture](https://imzbf.github.io/md-editor-rt/imgs/mark_emoji.gif)

## 🤗 Code

```vue
<template>
  <MdEditor v-model="text" />![Picture](https://imzbf.github.io/md-editor-rt/imgs/mark_emoji.gif)
</template>

<script setup>
import { ref } from 'vue';
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';![Picture](https://imzbf.github.io/md-editor-rt/imgs/mark_emoji.gif)

const text = ref('Hello Editor!');
</script>
```
"""

if __name__ == "__main__":
    ...
    print(get_all_image_url(TEXT))

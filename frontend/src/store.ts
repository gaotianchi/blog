import { Index, Document } from "flexsearch";
import { reactive, ref, type Ref } from "vue";
import type {
	ArticleEditorLocalAndRemote as EditorLocalAndRemote,
	ArticleSeriesLocalAndRemote,
	SettingStatus,
	Confirm,
	Article,
	ArticleCard,
	Series,
	SeriesArticleCount,
} from "@/typing";
import { defaultConfirm, defaultSettingStatus } from "@/defaults";
import { defaultArticle, defaultSeries } from "@/defaults";
export const editorLocalAndRemote: EditorLocalAndRemote = reactive({
	0: { local: { ...defaultArticle }, remote: { ...defaultArticle } },
});
export const articleSerieLocalAndRemote: ArticleSeriesLocalAndRemote = reactive(
	{ 0: { local: { ...defaultSeries }, remote: { ...defaultSeries } } }
);
export const settingStatus: SettingStatus = reactive({
	...defaultSettingStatus,
});
export const messageProp: Ref<string> = ref("");
export const confirmProp: Confirm = reactive({ ...defaultConfirm });
export const seriesArticleCount: Ref<SeriesArticleCount[]> = ref([]);
export const remoteArticle: Article = reactive({ ...defaultArticle });
export const remoteSeries: Series = reactive({ ...defaultSeries });
export const allRemoteArticleCards: Ref<ArticleCard[]> = ref([]);
export const allRemoteSeries: Ref<Series[]> = ref([]);
export const tagIndex = new Index({ tokenize: "forward" });
export const articleCardIndex = new Document({
	document: {
		id: "id",
		tag: "tags",
		index: [
			{
				field: "title",
				tokenize: "full",
			},
			{
				field: "author",
				tokenize: "strict",
			},
		],
	},
});

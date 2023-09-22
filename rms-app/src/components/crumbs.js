import { computed } from 'vue';
import { route } from './side-navigation.vue';

const crumbs = computed(() => {
route.matched;
});


import { TrashIcon } from "@heroicons/vue/24/outline";
import { InformationCircleIcon } from "@heroicons/vue/24/outline"

import { markRaw } from "vue";

const ICON = {
  DANGER: {
    component: markRaw(TrashIcon),
    class: "h-6 w-6 text-red-600",
    backgroudClass: "bg-red-100",
  },
  INFO: {
    component: markRaw(InformationCircleIcon),
    class: "h-6 w-6 text-blue-600",
    backgroudClass: "bg-blue-100"
  },
};

const BUTTON_COLOR = {
  DEFAULT: "text-gray-700 border border-gray-300 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2",
  DANGER: "text-white border border-transparent bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-offset-2",
  INFO: "text-white border border-transparent bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
};

const FORM = {
  INPUT: "custom-input w-[200px] p-inputtext-sm font-semibold shadow-sm focus:ring-blue-600 focus:border-blue-500 block w-full sm:text-sm border-gray-300 rounded-md",
  DROPDOWN: "custom-input w-[200px] p-inputtext-sm font-semibold w-full",
}

export {
  ICON,
  BUTTON_COLOR,
  FORM
};

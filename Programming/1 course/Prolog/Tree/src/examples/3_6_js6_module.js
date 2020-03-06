"use strict";

const localConst = "local";
function localFunction() {
	println(localConst);
}


export const exportedConst = "exported";
export function exportedFunction() {
	println(exportedConst);
}

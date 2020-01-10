
/* b_110, b_120, b_210, b_230, b_240, b_260, b_290, b_270, b_310, b_320, b_330, b_370, b_380, b_390, b_3A0, b_3B0, b_410, b_420, b_510, b_520, b_610 */

setztVoraus(b_110, []).
setztVoraus(b_120, []).
setztVoraus(b_210, []).
setztVoraus(b_230, []).
setztVoraus(b_240, [b_210]).
setztVoraus(b_260, [b_110]).
setztVoraus(b_270, [b_110, b_210, b_240]).
setztVoraus(b_290, [b_110, b_120, b_210, b_240, b_270]).
setztVoraus(b_310, [b_210, b_230]).
setztVoraus(b_320, [b_310]).
setztVoraus(b_330, [b_110]).
setztVoraus(b_370, [b_110, b_120, b_210, b_230, b_240, b_260, b_310]).
setztVoraus(b_380, [b_110, b_120, b_210, b_230, b_240, b_260, b_310, b_330, b_270]).
setztVoraus(b_390, [b_110]).
setztVoraus(b_3A0, [b_110, b_120, b_210, b_230, b_240, b_260, b_310, b_330, b_390]).
setztVoraus(b_3B0, [b_110, b_120, b_210, b_230, b_240, b_270, b_290, b_310]).
setztVoraus(b_410, []).
setztVoraus(b_420, [b_110, b_210, b_230, b_410]).
setztVoraus(b_510, [b_110, b_120, b_210, b_230, b_240, b_260, b_270, b_290, b_310, b_320, b_330, b_370, b_390]).
setztVoraus(b_520, [b_110, b_120, b_210, b_230, b_240, b_260, b_270, b_290, b_310, b_320, b_330, b_370, b_380, b_390, b_3A0, b_3B0, b_410, b_420, b_510]).
setztVoraus(b_610, []).

